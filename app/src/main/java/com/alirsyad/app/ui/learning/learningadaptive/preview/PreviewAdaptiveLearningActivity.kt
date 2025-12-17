package com.alirsyad.app.ui.learning.learningadaptive.preview

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.model.adaptive.CheckAnswer
import com.alirsyad.app.data.model.adaptive.DataQuestion
import com.alirsyad.app.data.model.adaptive.PreviewRequest
import com.alirsyad.app.data.model.params.DataParams
import com.alirsyad.app.data.model.previewadaptive.DataPreviewAdaptive
import com.alirsyad.app.data.state.PreviewAdaptiveState
import com.alirsyad.app.databinding.ActivityPreviewAdaptiveLearningBinding
import com.alirsyad.app.ui.adapter.PreviewAnswerAdapter
import com.alirsyad.app.ui.learning.learningadaptive.detail.DetailLearningAdaptiveViewModel
import com.alirsyad.app.ui.learning.learningadaptive.question.QuestionAdaptiveActivity
import com.alirsyad.app.utils.Constant
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.Utils.setupStatusBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewAdaptiveLearningActivity : AppCompatActivity() {

    private var data : DataParams? = null
    private var id : Int? = null
    private var name : String? = null
    private var dataQuestion : DataQuestion? = null
    private var previewRequest : PreviewRequest? = null
    private var checkAnswer: CheckAnswer? = null

    private val binding: ActivityPreviewAdaptiveLearningBinding by lazy {
        ActivityPreviewAdaptiveLearningBinding.inflate(layoutInflater)
    }

    private val adapter: PreviewAnswerAdapter by lazy {
        PreviewAnswerAdapter(this)
    }

    private val viewModel: DetailLearningAdaptiveViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupDataIntent()
        setupToolbar(dataQuestion)
        setupViewModel()
        setupStatusBar(this)
        setupListener()
    }

    private fun setupDataIntent() {
        data = intent.getParcelableExtra(Constant.Intent.DATA)
        id = intent.getIntExtra(Constant.Intent.ID, 0)
        name =  intent.getStringExtra(Constant.Intent.NAME)
        dataQuestion =  intent.getParcelableExtra(Constant.Intent.DATA_QUESTION)
        previewRequest =  intent.getParcelableExtra(Constant.Intent.PREVIEW_REQUEST)
        checkAnswer =  intent.getParcelableExtra(Constant.Intent.CHECK_ANSWER)
    }

    private fun setupViewModel() {
        viewModel.clearQuestion()
        viewModel.getPreview(previewRequest)
        viewModel.preview.observe(this) { response ->
            when(response) {
                is PreviewAdaptiveState.Loading -> setupLoading(true)
                is PreviewAdaptiveState.Result -> setDataPreview(response.data.data)
                is PreviewAdaptiveState.Error -> setupError(response.error)
            }
        }
    }

    private fun setDataPreview(data: DataPreviewAdaptive?) {
        setupLoading(false)
        binding.rvQuestion.also {
            it.setHasFixedSize(true)
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this)
            data?.listSoal?.size?.let { it1 -> it.setItemViewCacheSize(it1) }
        }

        data?.listSoal?.let { adapter.setData(it) }
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

    private fun setupListener() = with(binding) {
        btnCancel.setOnClickListener {
            finish()
        }

        if (checkAnswer?.nextPaketSoalId != null) {
            if (checkAnswer?.status == Constant.Status.PASS) {
                btnSubmit.text = getString(R.string.title_next_level)
            } else {
                btnSubmit.text = getString(R.string.title_btn_lets_try_again)
            }
            btnSubmit.setOnClickListener {
                startActivity(Intent(applicationContext, QuestionAdaptiveActivity::class.java).also {
                    it.putExtra(Constant.Intent.DATA, data)
                    it.putExtra(Constant.Intent.ID, data?.id)
                    it.putExtra(Constant.Intent.NAME, data?.name)
                    it.putExtra(Constant.Intent.PREVIEW_NEXT_ID, checkAnswer?.nextPaketSoalId)
                })
                finish()
            }
        } else {
            finish()
        }
    }

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        loading.setLoading(isLoading)
    }

    private fun setupError(error: Throwable) {
        setupLoading(false)
        ErrorUtils.setupError(this, error, showBottomSheet = {
            DibBottomSheet(ErrorType.ERROR_SYSTEM.name, this)
                .show(supportFragmentManager, ErrorType.ERROR_SYSTEM.name)
        })
    }
}