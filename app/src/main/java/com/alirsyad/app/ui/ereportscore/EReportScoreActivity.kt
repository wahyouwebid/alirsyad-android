package com.alirsyad.app.ui.ereportscore

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.model.courses.DataCourse
import com.alirsyad.app.data.model.ereport.EReportCurrentScore
import com.alirsyad.app.data.model.ereport.EReportScore
import com.alirsyad.app.data.model.toolbar.DataToolbar
import com.alirsyad.app.data.state.CourseState
import com.alirsyad.app.data.state.EReportScoreState
import com.alirsyad.app.databinding.ActivityEreportScoreBinding
import com.alirsyad.app.ui.ereportscore.adapter.EReportCourseAdapter
import com.alirsyad.app.ui.ereportscore.adapter.EReportModuleAdapter
import com.alirsyad.app.utils.Constant.Intent.DATA
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.Utils.setupStatusBar
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EReportScoreActivity : AppCompatActivity() {

    private val binding: ActivityEreportScoreBinding by lazy {
        ActivityEreportScoreBinding.inflate(layoutInflater)
    }

    private val viewModel: EReportScoreViewModel by viewModels()
    private var dataCourse: DataCourse? = null

    private val courseAdapter: EReportCourseAdapter by lazy {
        EReportCourseAdapter(0){ item -> getModule(item)}
    }

    private val moduleAdapter: EReportModuleAdapter by lazy {
        EReportModuleAdapter(this)
    }

    private val eReportScore: EReportCurrentScore? by lazy {
        intent.getParcelableExtra(DATA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupAdapter()
        setupObserveViewModel()
        setupToolbar()
        setupStatusBar(this)
    }

    private fun setupToolbar() {
        with(binding) {
            toolbar.setToolbar(
                getString(R.string.title_your_score),
                DataToolbar(
                    viewModel.getIsPengunjung(),
                    viewModel.getTingkat(),
                    viewModel.getKelas()
                )
            ) { finish() }
        }
    }

    private fun setupAdapter() {
        with(binding) {
            rvSubjectCourse.also {
                it.adapter = courseAdapter
                it.layoutManager = LinearLayoutManager(this@EReportScoreActivity, LinearLayoutManager.HORIZONTAL, false)
            }

            rvModule.also {
                it.adapter = moduleAdapter
                it.layoutManager = LinearLayoutManager(this@EReportScoreActivity)
            }
        }
    }


    private fun setupObserveViewModel() {
        viewModel.getCourse()
        viewModel.course.observe(this) {
            when (it) {
                is CourseState.Loading -> {
                    setupLoadingSubjectCourse(true)
                }
                is CourseState.Result -> {
                    setupLoadingSubjectCourse(false)
                    setDataSubjectCourse(it.data.data)
                }
                is CourseState.Error -> {
                    setupLoadingSubjectCourse(false)
                    setupErrorSubjectCourse(it.error)
                }
            }
        }

        viewModel.module.observe(this) {
            when (it) {
                is EReportScoreState.Loading -> {
                    setupLoadingModule(true)
                }
                is EReportScoreState.Result -> {
                    setupLoadingModule(false)
                    setDataModule(it.data.data)
                }
                is EReportScoreState.Error -> {
                    setupLoadingModule(false)
                    setupErrorSubjectCourse(it.error)
                }
            }
        }
    }

    private fun setDataSubjectCourse(data: List<DataCourse>){
        if (data.isNotEmpty()) {
            if (eReportScore == null) {
                dataCourse = data[0]
                courseAdapter.setData(data)
                viewModel.getData(data[0].id)
            } else {
                val filtered = data.filter { it.name == eReportScore?.mataPelajaranName }
                val position = data.indexOf(filtered[0])
                dataCourse = filtered[0]
                courseAdapter.setData(data)
                courseAdapter.setPosition(position)
                viewModel.getData(filtered[0].id)
                binding.rvSubjectCourse.scrollToPosition(position)
            }
        }

    }

    private fun setDataModule(data: List<EReportScore>) = with(binding) {
        if (data.isNotEmpty()) {
            cvModule.show()
            moduleAdapter.setData(data)
            dibEmpty.hide()
        } else {
            setNoData()
        }
    }

    private fun setNoData() = with(binding){
        setupLoadingModule(false)
        shModule.hide()
        cvModule.hide()
        dibEmpty.show()
        dibEmpty.setData(
            getString(R.string.title_e_report_score_empty_state, dataCourse?.name),
            getString(R.string.title_e_report_score_empty_state_description)
        )
    }

    private fun setupLoadingSubjectCourse(loading: Boolean) {
        with(binding) {
            if (loading) {
                rvSubjectCourse.hide()
                shSubjectsClass.show()
                shSubjectsClass.startShimmer()
            } else {
                rvSubjectCourse.show()
                shSubjectsClass.hide(true)
                shSubjectsClass.stopShimmer()
            }
        }
    }

    private fun setupLoadingModule(loading: Boolean) {
        with(binding) {
            if (loading) {
                rvModule.hide()
                shModule.show()
                shModule.startShimmer()
            } else {
                rvModule.show()
                shModule.hide(true)
                shModule.stopShimmer()
            }
        }
    }

    private fun setupErrorSubjectCourse(error: Throwable) {
        setupLoadingSubjectCourse(false)
        ErrorUtils.setupError(this, error,
            showBottomSheet = {
                DibBottomSheet(ErrorType.ERROR_PROCESS_DATA.name, this)
                    .show(this.supportFragmentManager, ErrorType.ERROR_PROCESS_DATA.name)
            },
            tryAgain = {
                viewModel.getCourse()
            }
        )
    }

    private fun getModule(item: DataCourse) {
        dataCourse = item
        viewModel.getData(item.id)
    }
}