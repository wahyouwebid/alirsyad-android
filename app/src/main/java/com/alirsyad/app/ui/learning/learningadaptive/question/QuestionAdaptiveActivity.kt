package com.alirsyad.app.ui.learning.learningadaptive.question

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.component.customview.DibPopUp
import com.alirsyad.app.data.entity.QuestionEntity
import com.alirsyad.app.data.model.adaptive.AnswerSelected
import com.alirsyad.app.data.model.adaptive.CheckAnswer
import com.alirsyad.app.data.model.adaptive.CheckAnswerRequest
import com.alirsyad.app.data.model.adaptive.DataQuestion
import com.alirsyad.app.data.model.adaptive.ListAnswer
import com.alirsyad.app.data.model.adaptive.PreviewRequest
import com.alirsyad.app.data.model.adaptive.Question
import com.alirsyad.app.data.model.params.DataParams
import com.alirsyad.app.data.state.CheckAnswerState
import com.alirsyad.app.data.state.QuestionState
import com.alirsyad.app.databinding.ActivityDetailLearningAdaptiveBinding
import com.alirsyad.app.ui.adapter.QuestionAdaptiveAdapter
import com.alirsyad.app.ui.learning.learningadaptive.preview.PreviewAdaptiveLearningActivity
import com.alirsyad.app.utils.Constant
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.Utils.setupStatusBar
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.show
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class QuestionAdaptiveActivity : AppCompatActivity(){

    private var data : DataParams? = null
    private var id : Int? = null
    private var name : String? = null
    private val viewModel: QuestionAdaptiveViewModel by viewModels()
    private var dataQuestion: DataQuestion? = null
    private var nextIdFromPreview: Int? = null
    private val adapter: QuestionAdaptiveAdapter by lazy {
        QuestionAdaptiveAdapter {  }
    }

    private val binding: ActivityDetailLearningAdaptiveBinding by lazy {
        ActivityDetailLearningAdaptiveBinding.inflate(layoutInflater)
    }

    private var previewRequest: PreviewRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupDataIntent()
        setupViewModel()
        setupObserveViewModel()
        setupStatusBar(this)
        setupListener()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    }

    private fun setupDataIntent() {
        data = intent.getParcelableExtra(Constant.Intent.DATA)
        id = intent.getIntExtra(Constant.Intent.ID, 0)
        name =  intent.getStringExtra(Constant.Intent.NAME)
        nextIdFromPreview =  intent.getIntExtra(Constant.Intent.PREVIEW_NEXT_ID, -1)
    }

    private fun setupToolbar(dataQuestion: DataQuestion?){
        with(binding){
            toolbar.setToolbar(
                title = data?.name,
                level = dataQuestion?.tingkatKesulitan
            ) {
                finish()
            }
        }
    }

    private fun setupViewModel(){
        if (nextIdFromPreview == null || nextIdFromPreview == -1) {
            data?.id?.let { viewModel.getData(it) }
        } else {
            nextIdFromPreview?.let { viewModel.getData(it) }
        }
    }

    private fun setupObserveViewModel(){
        viewModel.question.observe(this) {
            when (it) {
                is QuestionState.Loading -> {
                    setupLoading(true)
                }
                is QuestionState.Result -> {
                    setupLoading(true)
                    dataQuestion = it.data.data
                    setupToolbar(it.data.data)
                    loadingRender()

                    val questions = it.data.data.listSoal

                    binding.rvQuestion.also { rv ->
                        rv.setHasFixedSize(true)
                        rv.adapter = adapter
                        rv.layoutManager = LinearLayoutManager(this@QuestionAdaptiveActivity)
                        rv.setItemViewCacheSize(questions.size)
                    }

                    adapter.setData(questions)
                    setNoData(questions)
                }
                is QuestionState.Error -> {
                    setupError(it.error)
                }
            }
        }

        viewModel.checkAnswer.observe(this) {
            when (it) {
                is CheckAnswerState.Loading -> {
                    setupLoading(true)
                }
                is CheckAnswerState.Result -> {
                    setupLoading(true)
                    val data = it.data.data
                    setSubmitSuccess(data)
                }
                is CheckAnswerState.Error -> {
                    setupError(it.error)
                }
            }
        }
    }

    private fun setupListener() {
        binding.btnSubmit.setOnClickListener {
            val unanswered = adapter.getUnanswered()

            if (unanswered.isNotEmpty()) {
                adapter.setHighlightUnanswered(true)
                binding.rvQuestion.scrollToPosition(0)
                DibPopUp(this).popupSubmittedIsEmpty()
                return@setOnClickListener
            }

            val answered = adapter.getAnswered()

            val checkList = answered.map {
                AnswerSelected(
                    id = it.id,
                    jawaban = it.answer
                )
            }

            val previewList = answered.map {
                ListAnswer(
                    id = it.id,
                    jawaban = it.answer,
                    listJawaban = it.jawaban ?: emptyList()
                )
            }

            val checkRequest = CheckAnswerRequest(
                paketSoalId = dataQuestion?.paketSoalId,
                userId = viewModel.getUserId(),
                listSoal = checkList
            )

            previewRequest = PreviewRequest(
                paketSoalId = dataQuestion?.paketSoalId,
                userId = viewModel.getUserId(),
                listSoal = previewList
            )

            viewModel.checkAnswer(checkRequest)
        }

        binding.btnCancel.setOnClickListener { finish() }

    }

    private fun setNoData(dataList: List<Question>?) {
        with(binding) {
            if (dataList?.isEmpty() == true) {
                rvQuestion.hide(true)
                dibEmpty.show()
                dibEmpty.setData(
                    getString(R.string.title_adaptive_learning_soal_empty_state, name),
                    getString(R.string.title_adaptive_learning_soal_empty_state_description)
                )
                btnCancel.hide(true)
                btnSubmit.hide(true)
            } else {
                rvQuestion.show()
                dibEmpty.hide()
                btnCancel.show()
                btnSubmit.show()
            }
        }
    }

    private fun setSubmitSuccess(checkAnswer: CheckAnswer) {
        if (checkAnswer.status == Constant.Status.PASS) {
            DibPopUp(this@QuestionAdaptiveActivity).popupSubmittedSuccess(
                checkAnswer.score.toString(),
                checkAnswer.nextTingkatKesulitan,
                dataQuestion?.listSoal?.size,
                { finish() },
                { nextQuestion(checkAnswer) },
                { checkTheStudy(checkAnswer) }
            )
        } else {
            DibPopUp(this@QuestionAdaptiveActivity).popupSubmittedFailed(
                checkAnswer.score.toString(),
                dataQuestion?.listSoal?.size,
                { finish() },
                { nextQuestion(checkAnswer) },
                { checkTheStudy(checkAnswer) }
            )
        }
    }

    private fun nextQuestion(checkAnswer: CheckAnswer) {
        if (checkAnswer.nextPaketSoalId == null) {
            finish()
        } else {
            binding.nsvQuestion.fullScroll(View.FOCUS_UP)
            viewModel.clearQuestion()
            Handler(Looper.getMainLooper()).postDelayed({
                viewModel.getData(checkAnswer.nextPaketSoalId)
            }, 500)
        }
    }

    private fun checkTheStudy(checkAnswer: CheckAnswer) {
        startActivity(
            Intent(this, PreviewAdaptiveLearningActivity::class.java).also {
                it.putExtra(Constant.Intent.DATA, data)
                it.putExtra(Constant.Intent.ID, id)
                it.putExtra(Constant.Intent.NAME, name)
                it.putExtra(Constant.Intent.DATA_QUESTION, dataQuestion)
                it.putExtra(Constant.Intent.CHECK_ANSWER, checkAnswer)
                it.putExtra(Constant.Intent.PREVIEW_REQUEST, previewRequest)
            }
        )
        finish()
    }

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        loading.setLoading(isLoading)
    }

    private fun loadingRender() = with(binding){
        Completable.timer(2, TimeUnit.SECONDS, Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                loading.setLoading(false)
                btnSubmit.show()
                btnCancel.show()
            }
    }

    private fun setupError(error: Throwable) {
        setupLoading(false)
        ErrorUtils.setupError(this, error, showBottomSheet = {
            DibBottomSheet(ErrorType.ERROR_SYSTEM.name, this)
                .show(supportFragmentManager, ErrorType.ERROR_SYSTEM.name)
        }, tryAgain = {
            if (nextIdFromPreview == null || nextIdFromPreview == -1) {
                getNewData()
            } else {
                getNewDataFromPrev()
            }
        })
    }

    private fun getNewData() {
        viewModel.clearQuestion()
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getData(data?.id)
        }, 500)
    }

    private fun getNewDataFromPrev() {
        viewModel.clearQuestion()
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getData(nextIdFromPreview)
        }, 500)
    }
}