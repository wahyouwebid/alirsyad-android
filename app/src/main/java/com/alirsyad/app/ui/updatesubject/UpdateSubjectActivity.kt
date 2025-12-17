package com.alirsyad.app.ui.updatesubject

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.model.params.DataParams
import com.alirsyad.app.data.model.update.DataUpdate
import com.alirsyad.app.data.state.UpdateState
import com.alirsyad.app.databinding.ActivityNewUpdatesBinding
import com.alirsyad.app.ui.adapter.NewUpdateGridAdapter
import com.alirsyad.app.ui.learning.learningmodule.detail.DetailLearningModuleActivity
import com.alirsyad.app.ui.learning.learningvideo.detail.DetailLearningVideoActivity
import com.alirsyad.app.utils.Constant.Intent.DATA
import com.alirsyad.app.utils.Constant.Intent.ID
import com.alirsyad.app.utils.Constant.Intent.NAME
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.Utils.setupStatusBar
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateSubjectActivity : AppCompatActivity() {

    private val binding: ActivityNewUpdatesBinding by lazy {
        ActivityNewUpdatesBinding.inflate(layoutInflater)
    }

    private val viewModel: UpdateSubjectViewModel by viewModels()

    private val adapterNewUpdateGrid: NewUpdateGridAdapter by lazy {
        NewUpdateGridAdapter { idCourse, nameCourse, idModule, nameModule, trigger ->
            if (trigger == "video") {
                detailVideo(idCourse, nameCourse, idModule, nameModule)
            } else {
                detailModule(idCourse, nameCourse, idModule, nameModule)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar()
        setupAdapter()
        setupObserveViewModel()
        setupViewModel()
        setupStatusBar(this)
    }

    private fun setupToolbar() {
        with(binding) {
            toolbar.setToolbar(
                getString(R.string.title_updated_subject),
            ) {
                finish()
            }
        }
    }

    private fun setupAdapter() {
        with(binding) {
            rvNewUpdateGrid.also {
                it.isNestedScrollingEnabled = false
                it.adapter = adapterNewUpdateGrid
                it.layoutManager = GridLayoutManager(this@UpdateSubjectActivity, 2)
            }
        }
    }

    private fun setupObserveViewModel() {
        viewModel.newUpdate.observe(this) {
            when (it) {
                is UpdateState.Loading -> {
                    setupLoadingNewUpdate(true)
                }
                is UpdateState.Result -> {
                    setupLoadingNewUpdate(false)
                    setDataUpdate(it.data.data)
                }
                is UpdateState.Error -> {
                    setupErrorNewUpdate(it.error)
                }
            }
        }
    }

    private fun setDataUpdate(data: List<DataUpdate>) {
        when {
            data.isEmpty() -> {
                setupIsEmptyNewUpdate()
            }
            else -> {
                adapterNewUpdateGrid.setData(data)
            }
        }
    }

    private fun setupLoadingNewUpdate(loading: Boolean) {
        with(binding) {
            if (loading) {
                rvNewUpdateGrid.hide(true)
                llEmpty.hide(true)
                shNewUpdateGrid.show()
                shNewUpdateGrid.startShimmer()
            } else {
                rvNewUpdateGrid.show()
                llEmpty.hide(true)
                shNewUpdateGrid.hide(true)
                shNewUpdateGrid.stopShimmer()
            }
        }
    }

    private fun setupIsEmptyNewUpdate() {
        with(binding) {
            llEmpty.show()
            rvNewUpdateGrid.hide(true)
            shNewUpdateGrid.hide(true)
        }
    }

    private fun setupErrorNewUpdate(error: Throwable) {
        setupIsEmptyNewUpdate()
        ErrorUtils.setupError(this, error,
            showBottomSheet = {
                DibBottomSheet(ErrorType.ERROR_PROCESS_DATA.name, this, onClickOk = {
                    viewModel.getNewUpdate()
                }).show(supportFragmentManager, ErrorType.ERROR_PROCESS_DATA.name)
            },
            tryAgain = {
                setupViewModel()
            }
        )
    }

    private fun setupViewModel() {
        viewModel.getNewUpdate()
    }

    private fun detailModule(idCourse: Int, nameCourse: String, idModule: Int, nameModule: String) {
        startActivity(Intent(this, DetailLearningModuleActivity::class.java).also {
            val params = DataParams(
                idModule,
                nameModule,
                "",
                ""
            )
            it.putExtra(DATA, params)
            it.putExtra(ID, idCourse)
            it.putExtra(NAME, nameCourse)
        })
    }

    private fun detailVideo(idCourse: Int, nameCourse: String, idModule: Int, nameModule: String) {
        startActivity(Intent(this, DetailLearningVideoActivity::class.java).also {
            val data = DataParams(
                idModule,
                nameModule,
                "",
                ""
            )
            it.putExtra(DATA, data)
            it.putExtra(ID, idCourse)
            it.putExtra(NAME, nameCourse)
        })
    }
}