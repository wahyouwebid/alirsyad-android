package com.alirsyad.app.ui.learning.learningmodule

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.model.module.DataModule
import com.alirsyad.app.data.model.params.DataParams
import com.alirsyad.app.data.model.toolbar.DataToolbar
import com.alirsyad.app.data.state.CourseModuleState
import com.alirsyad.app.databinding.ActivityLearningModuleBinding
import com.alirsyad.app.ui.adapter.LearningModuleAdapter
import com.alirsyad.app.ui.learning.learningmodule.detail.DetailLearningModuleActivity
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
class LearningModuleActivity : AppCompatActivity() {

    private val binding: ActivityLearningModuleBinding by lazy {
        ActivityLearningModuleBinding.inflate(layoutInflater)
    }

    private val viewModel: LearningModuleViewModel by viewModels()

    private val adapter: LearningModuleAdapter by lazy {
        LearningModuleAdapter(
            this,
            viewModel.getIsPengunjung(),
        ) { item -> detailModule(item) }
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
    }

    private fun setupAdapter() {
        with(binding) {
            rvLearning.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(this@LearningModuleActivity)
            }
        }
    }

    private fun setupObserveViewModel() {
        viewModel.module.observe(this) {
            when (it) {
                is CourseModuleState.Loading -> {
                    setupLoading(true)
                }
                is CourseModuleState.Result -> {
                    setupLoading(false)
                    adapter.setData(it.data.data)
                    setupNoData(it.data.data)
                }
                is CourseModuleState.Error -> {
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
                }).show(supportFragmentManager, ErrorType.ERROR_PROCESS_DATA.name)
            },
            tryAgain = {
                data?.id?.let { viewModel.getData(it) }
            }
        )
    }

    private fun detailModule(item: DataModule) {
        startActivity(Intent(this, DetailLearningModuleActivity::class.java).also {
            val params = DataParams(
                item.id,
                item.name,
                data?.tingkat,
                data?.jenjang
            )
            it.putExtra(DATA, params)
            it.putExtra(ID, data?.id)
            it.putExtra(NAME, data?.name)
        })
    }

    private fun setupNoData(dataList: List<DataModule>) {
        with(binding) {
            if (dataList.isEmpty()) {
                cvCard.hide(true)
                dibEmpty.show()
                dibEmpty.setData(
                    getString(R.string.title_module_empty_state, data?.name),
                    getString(R.string.title_module_empty_state_description)
                )
            } else {
                cvCard.show()
                dibEmpty.hide()
            }
        }
    }

    val data: DataParams? by lazy {
        intent.getParcelableExtra(DATA)
    }
}