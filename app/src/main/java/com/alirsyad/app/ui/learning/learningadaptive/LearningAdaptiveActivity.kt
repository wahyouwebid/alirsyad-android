package com.alirsyad.app.ui.learning.learningadaptive

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.model.adaptive.AdaptiveModule
import com.alirsyad.app.data.model.params.DataParams
import com.alirsyad.app.data.model.toolbar.DataToolbar
import com.alirsyad.app.data.state.ResponseArrayState
import com.alirsyad.app.databinding.ActivityAdaptiveLearningBinding
import com.alirsyad.app.ui.adapter.LearningAdaptiveAdapter
import com.alirsyad.app.utils.Constant.Intent.DATA
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.Utils.setupStatusBar
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LearningAdaptiveActivity : AppCompatActivity() {

    private val binding: ActivityAdaptiveLearningBinding by lazy {
        ActivityAdaptiveLearningBinding.inflate(layoutInflater)
    }

    private val viewModel: LearningAdaptiveViewModel by viewModels()

    private val adapter: LearningAdaptiveAdapter by lazy {
        LearningAdaptiveAdapter(this, data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupAdapter()
        setupObserveViewModel()
        setToolbar()
        setupStatusBar(this)
    }

    override fun onResume() {
        super.onResume()
        data?.id?.let { viewModel.getData(it) }
        viewModel.clearQuestion()
    }

    private fun setupAdapter() {
        with(binding) {
            rvLearning.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(this@LearningAdaptiveActivity)
            }
        }
    }

    private fun setupObserveViewModel() {
        viewModel.questionPackage.observe(this) {
            when (it) {
                is ResponseArrayState.Loading -> {
                    setupLoading(true)
                }
                is ResponseArrayState.Result -> {
                    setupLoading(false)
                    adapter.setData(it.data.data)
                    setupNoData(it.data.data)
                }
                is ResponseArrayState.Error -> {
                    setupError(it.error)
                }
            }
        }
    }

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        loading.setLoading(isLoading)
    }

    private fun setToolbar() {
        with(binding) {
            toolbar.setToolbar(
                data?.name,
                DataToolbar(
                    viewModel.getIsPengunjung(),
                    viewModel.getTingkat(),
                    viewModel.getKelas()
                )
            ) { finish() }
        }
    }

    private fun setupError(error: Throwable) {
        setupLoading(false)
        ErrorUtils.setupError(this, error,
            showBottomSheet = {
                DibBottomSheet(ErrorType.ERROR_PROCESS_DATA.name, this, onClickOk = {
                    data?.id?.let { viewModel.getData(it) }
                    viewModel.clearQuestion()
                }).show(supportFragmentManager, ErrorType.ERROR_PROCESS_DATA.name)
            },
            tryAgain = {
                data?.id?.let { viewModel.getData(it) }
            }
        )
    }

    private fun setupNoData(dataList: List<AdaptiveModule>){
        with(binding) {
            if (dataList.isEmpty()) {
                cvData.hide(true)
                dibEmpty.show()
                dibEmpty.setData(
                    getString(R.string.title_adaptive_learning_empty_state, data?.name),
                    getString(R.string.title_adaptive_learning_empty_state_description)
                )
            } else {
                cvData.show()
                dibEmpty.hide()
            }
        }
    }

    val data: DataParams? by lazy {
        intent.getParcelableExtra(DATA)
    }
}