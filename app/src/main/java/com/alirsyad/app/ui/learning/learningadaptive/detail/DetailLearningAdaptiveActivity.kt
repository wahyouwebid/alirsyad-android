package com.alirsyad.app.ui.learning.learningadaptive.detail

import android.content.Intent
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
import com.alirsyad.app.data.model.params.DataParams
import com.alirsyad.app.data.state.CheckAnswerState
import com.alirsyad.app.data.state.QuestionState
import com.alirsyad.app.databinding.ActivityDetailLearningAdaptiveBinding
import com.alirsyad.app.ui.adapter.QuestionAdapterUtilsNew
import com.alirsyad.app.ui.learning.learningadaptive.preview.PreviewAdaptiveLearningActivity
import com.alirsyad.app.utils.Constant
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.Utils.setupStatusBar
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.show
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class DetailLearningAdaptiveActivity : AppCompatActivity(){

    private var data : DataParams? = null
    private var id : Int? = null
    private var name : String? = null
    private val viewModel: DetailLearningAdaptiveViewModel by viewModels()
    private var dataQuestion: DataQuestion? = null
    private var questionList: MutableList<QuestionEntity> = arrayListOf()
    private var nextIdFromPreview: Int? = null

    private val questionAdapter: QuestionAdapterUtilsNew by lazy {
        QuestionAdapterUtilsNew { updateData -> setUpdateQuestion(updateData) }
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
                    viewModel.insertQuestion(it.data.data.listSoal)
                    dataQuestion = it.data.data
                    setupToolbar(it.data.data)
                    loadingRender()
                }
                is QuestionState.Error -> {
                    setupError(it.error)
                }
            }
        }

        viewModel.insertQuestion.observe(this) { isInserted ->
            setupLoading(true)
            if (isInserted == 1) {
                setupLoading(true)
                viewModel.getQuestion()
            }
        }

        viewModel.getQuestion.observe(this) { data ->
            setData(data)
            setNoData(data)
        }

        viewModel.updateQuestion.observe(this) { isUpdated ->
            print(isUpdated)
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

    private fun setNoData(dataList: List<QuestionEntity>?) {
        with(binding) {
            if (dataList?.isEmpty() == true) {
                rvQuestion.hide(true)
                dibEmpty.show()
                dibEmpty.setData(
                    getString(R.string.title_adaptive_learning_soal_empty_state, name),
                    getString(R.string.title_adaptive_learning_soal_empty_state_description)
                )
            } else {
                rvQuestion.show()
                dibEmpty.hide()
            }
        }
    }

    private fun setSubmitSuccess(checkAnswer: CheckAnswer) {
        if (checkAnswer.status == Constant.Status.PASS) {
            DibPopUp(this@DetailLearningAdaptiveActivity).popupSubmittedSuccess(
                checkAnswer.score.toString(),
                checkAnswer.nextTingkatKesulitan,
                questionList.size,
                { finish() },
                { nextQuestion(checkAnswer) },
                { checkTheStudy(checkAnswer) }
            )
        } else {
            DibPopUp(this@DetailLearningAdaptiveActivity).popupSubmittedFailed(
                checkAnswer.score.toString(),
                questionList.size,
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
            questionAdapter.checkAnswer(false)
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

    private fun setData(data: List<QuestionEntity>) = with(binding) {
        questionList.clear()
        questionList.addAll(data)
        rvQuestion.also {
            it.setHasFixedSize(true)
            it.adapter = questionAdapter
            it.layoutManager = LinearLayoutManager(this@DetailLearningAdaptiveActivity)
            it.setItemViewCacheSize(data.size)
        }

        Observable.fromCallable{
            questionAdapter.setData(data)
        }.subscribeOn(Schedulers.newThread()).subscribe()

    }

    private fun setUpdateQuestion(question: QuestionEntity) {
        viewModel.updateQuestion(question)
    }

    private fun setupListener() = with(binding) {
        btnSubmit.setOnClickListener {
            questionAdapter.checkAnswer(true)
            viewModel.getQuestion()
            val unanswered = questionAdapter.checkAnswerIsEmpty()
            val answer: MutableList<AnswerSelected> = ArrayList()
            val preview: MutableList<ListAnswer> = ArrayList()
            if (unanswered.isEmpty()) {
                questionAdapter.getDataAnswer().forEach {
                    answer.add(AnswerSelected(it.id, it.answered))
                    preview.add(ListAnswer(
                        it.id,
                        it.answered,
                        listOf(
                            it.answerA,
                            it.answerB,
                            it.answerC,
                            it.answerD,
                            it.answerE
                        )
                    ))
                }
                val checkRequest = CheckAnswerRequest(
                    dataQuestion?.paketSoalId,
                    viewModel.getUserId(),
                    answer
                )
                previewRequest = PreviewRequest(
                    dataQuestion?.paketSoalId,
                    viewModel.getUserId(),
                    preview
                )

                viewModel.checkAnswer(checkRequest)
            } else {
                DibPopUp(this@DetailLearningAdaptiveActivity).popupSubmittedIsEmpty()
            }
        }

        btnCancel.setOnClickListener { finish() }
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