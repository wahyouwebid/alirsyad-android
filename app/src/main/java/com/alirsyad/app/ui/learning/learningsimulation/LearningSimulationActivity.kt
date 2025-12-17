package com.alirsyad.app.ui.learning.learningsimulation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.model.params.DataParams
import com.alirsyad.app.data.model.simulation.Simulasi
import com.alirsyad.app.data.model.toolbar.DataToolbar
import com.alirsyad.app.data.state.CourseSimulationState
import com.alirsyad.app.databinding.ActivityLearningSimulationBinding
import com.alirsyad.app.ui.adapter.LearningSimulationAdapter
import com.alirsyad.app.ui.learning.learningsimulation.detail.DetailLearningSimulationActivity
import com.alirsyad.app.utils.Constant.Intent.DATA
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.Utils.setupStatusBar
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LearningSimulationActivity : AppCompatActivity() {

    private val binding: ActivityLearningSimulationBinding by lazy {
        ActivityLearningSimulationBinding.inflate(layoutInflater)
    }

    private val adapter: LearningSimulationAdapter by lazy {
        LearningSimulationAdapter(
            this,
            viewModel.getIsPengunjung()
        ) { item -> detailSimulation(item) }
    }

    private val viewModel: LearningSimulationViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setToolbar()
        setupAdapter()
        setupObserveViewModel()
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
                it.layoutManager = GridLayoutManager(this@LearningSimulationActivity, 2)
                it.setHasFixedSize(true)
            }
        }
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

    private fun setupObserveViewModel() {
        viewModel.simulation.observe(this) {
            when (it) {
                is CourseSimulationState.Loading -> {
                    setupLoading(true)
                }
                is CourseSimulationState.Result -> {
                    setupLoading(false)
                    adapter.setData(it.data.data)
                    setupNoData(it.data.data)
                }
                is CourseSimulationState.Error -> {
                    setupError(it.error)
                }
            }
        }
    }

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        loading.setLoading(isLoading)
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

    private fun detailSimulation(item: Simulasi) {
        startActivity(Intent(this, DetailLearningSimulationActivity::class.java).also {
            val data = DataParams(
                item.id,
                item.simulasiUrl,
                data?.tingkat,
                data?.jenjang
            )
            it.putExtra(DATA, data)
        })
    }

    private fun setupNoData(dataList: List<Simulasi>) {
        with(binding) {
            if (dataList.isEmpty()) {
                clData.hide(true)
                dibEmpty.show()
                dibEmpty.setData(
                    getString(R.string.title_simulation_empty_state, data?.name),
                    getString(R.string.title_simulation_empty_state_description)
                )
            } else {
                clData.show()
                dibEmpty.hide()
            }
        }
    }

    val data: DataParams? by lazy {
        intent.getParcelableExtra("data")
    }
}