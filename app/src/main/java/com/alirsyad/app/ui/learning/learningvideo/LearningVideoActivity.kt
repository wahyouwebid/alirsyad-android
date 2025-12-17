package com.alirsyad.app.ui.learning.learningvideo

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.model.params.DataParams
import com.alirsyad.app.data.model.toolbar.DataToolbar
import com.alirsyad.app.data.model.video.DataVideo
import com.alirsyad.app.data.state.CourseVideoState
import com.alirsyad.app.databinding.ActivityLearningVideoBinding
import com.alirsyad.app.ui.adapter.LearningVideoAdapter
import com.alirsyad.app.ui.learning.learningvideo.detail.DetailLearningVideoActivity
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.Utils.setupStatusBar
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LearningVideoActivity : AppCompatActivity() {

    private val binding: ActivityLearningVideoBinding by lazy {
        ActivityLearningVideoBinding.inflate(layoutInflater)
    }

    private val adapter: LearningVideoAdapter by lazy {
        LearningVideoAdapter(
            this,
            viewModel.getIsPengunjung()
        ) { item -> detailModule(item) }
    }

    private val viewModel: LearningVideoViewModel by viewModels()

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
    }

    private fun setupAdapter() {
        with(binding) {
            rvLearning.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(this@LearningVideoActivity)
            }
        }
    }

    private fun setupObserveViewModel() {
        viewModel.video.observe(this) {
            when (it) {
                is CourseVideoState.Loading -> {
                    setupLoading(true)
                }
                is CourseVideoState.Result -> {
                    setupLoading(false)
                    adapter.setData(it.data.data)
                    setupNoData(it.data.data)
                }
                is CourseVideoState.Error -> {
                    setupErrorInProgress(it.error)
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
                    data?.jenjang,
                )
            ) {
                finish()
            }
        }
    }


    private fun setupErrorInProgress(error: Throwable) {
        setupLoading(false)
        ErrorUtils.setupError(this, error,
            showBottomSheet = {
                DibBottomSheet(ErrorType.ERROR_PROCESS_DATA.name, this, onClickOk = {
                    data?.id?.let { viewModel.getData(it) }
                }).show(supportFragmentManager, ErrorType.ERROR_PROCESS_DATA.name)
            },
            tryAgain = {
                data?.id?.let { viewModel.getData(it) }
            }
        )
    }

    private fun detailModule(item: DataVideo) {
        startActivity(Intent(this, DetailLearningVideoActivity::class.java).also {
            val data = DataParams(
                item.id,
                item.name,
                data?.tingkat,
                data?.jenjang
            )
            it.putExtra("data", data)
        })
    }

    private fun setupNoData(dataList: List<DataVideo>) {
        with(binding) {
            if (dataList.isEmpty()) {
                cvCard.hide(true)
                dibEmpty.show()
                dibEmpty.setData(
                    getString(R.string.title_video_empty_state, data?.name),
                    getString(R.string.title_video_empty_state_description)
                )
            } else {
                cvCard.show()
                dibEmpty.hide()
            }
        }
    }

    val data: DataParams? by lazy {
        intent.getParcelableExtra("data")
    }
}