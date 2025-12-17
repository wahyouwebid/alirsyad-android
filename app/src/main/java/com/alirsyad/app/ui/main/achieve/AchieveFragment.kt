package com.alirsyad.app.ui.main.achieve

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.model.achievement.Achievement
import com.alirsyad.app.data.model.achievement.Simulasi
import com.alirsyad.app.data.model.courses.DataCourse
import com.alirsyad.app.data.model.params.DataParams
import com.alirsyad.app.data.state.AchievementSimulationState
import com.alirsyad.app.data.state.CourseState
import com.alirsyad.app.databinding.FragmentAchieveBinding
import com.alirsyad.app.ui.MainViewModel
import com.alirsyad.app.ui.adapter.AchievementSimulationAdapter
import com.alirsyad.app.ui.learning.learningsimulation.detail.DetailLearningSimulationActivity
import com.alirsyad.app.utils.Constant.EMPTY_STRING
import com.alirsyad.app.utils.Constant.Intent.DATA
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.setFontBold
import com.alirsyad.app.utils.setFontRegular
import com.alirsyad.app.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AchieveFragment : Fragment() {

    private val binding: FragmentAchieveBinding by lazy {
        FragmentAchieveBinding.inflate(layoutInflater)
    }

    private val adapter: AchievementSimulationAdapter by lazy {
        AchievementSimulationAdapter { item -> detailSimulation(item) }
    }

    private val viewModel: MainViewModel by viewModels()
    private var tabPosition: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupToolbar()
        setupObserveViewModel()
    }

    private fun setupToolbar() {
        with(binding) {
            toolbar.setToolbar(getString(R.string.title_achieve))
        }
    }

    override fun onResume() {
        super.onResume()
        setupViewModel()
    }

    private fun setupAdapter() {
        with(binding) {
            rvAchievement.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun setupViewModel() {
        viewModel.getSubjectCourse()
    }

    private fun setupObserveViewModel() {
        viewModel.subjectCourse.observe(viewLifecycleOwner) {
            when (it) {
                is CourseState.Loading -> {
                    setupLoadingSubjectCourse(true)
                }
                is CourseState.Result -> {
                    setupLoadingSubjectCourse(false)
                    setDataSubjectCourse(it.data.data)
                }
                is CourseState.Error -> {
                    setupError(it.error)
                }
            }
        }

        viewModel.achievement.observe(viewLifecycleOwner) {
            when (it) {
                is AchievementSimulationState.Loading -> {
                    setupLoadingAchievement(true)
                }
                is AchievementSimulationState.Result -> {
                    setupLoadingAchievement(false)
                    setDataAchievement(it.data.data)
                }
                is AchievementSimulationState.Error -> {
                    setupError(it.error)
                }
            }
        }
    }

    private fun setDataAchievement(data: Achievement?) = with(binding) {
        clContent.show()
        shAchievement.hide(true)
        tvLabelAchievement.text = String.format("${getString(R.string.title_achievement_of)} ${data?.mataPelajaran}")
        tvValueAchievement.text = String.format("${data?.progress?.done}/${data?.progress?.total}")
        pbAchievement.progress = data?.progress?.percentage!!
        if (data.simulasis.isNotEmpty()) {
            adapter.setData(data.simulasis)
            dibEmpty.hide(true)
        } else {
            cvAchievement.hide(true)
            dibEmpty.show()
            dibEmpty.setData(
                getString(R.string.title_achievement_empty_state),
                getString(R.string.title_achievement_empty_state_description)
            )
        }
    }

    private fun setDataSubjectCourse(data: List<DataCourse>) = with(binding) {
        val classOfLevel = viewModel.getTingkatId()
        val dataByTematik = data.filter { it.name == "Tematik" }
        val dataByMtkIpa = data.filter { it.name == "Matematika" || it.name == "IPA" }.sortedByDescending { it.name }

        when (classOfLevel) {
            in 1..3 -> setWithoutTab(dataByTematik)
            in 4..9 -> setWithTab(dataByMtkIpa)
            else -> setWithoutTab(data)
        }
    }

    private fun setWithoutTab(dataByTematik: List<DataCourse>) = with(binding){
        if (dataByTematik.isNotEmpty()){
            tvLabelAchievement.show()
            tvMatematika.hide(true)
            tvMatematika.hide(true)
            viewModel.getAchievement(dataByTematik[0].id)
        }
    }

    private fun setWithTab(data: List<DataCourse>) = with(binding){
        when {
            data.size > 1 -> {
                tvLabelAchievement.hide()
                tvMatematika.show()
                tvIpa.show()
                viewModel.getAchievement(data[tabPosition].id)
                setTabListener(data)
            }

            data.size == 1 -> {
                tvLabelAchievement.show()
                tvMatematika.hide(true)
                tvMatematika.hide(true)
                viewModel.getAchievement(data[0].id)
            }

            else -> {
                tvLabelAchievement.hide(true)
                tvMatematika.hide(true)
                tvMatematika.hide(true)
                cvFinishedSimulation.hide(true)
                cvAchievement.hide(true)
            }
        }
    }

    private fun setTabListener(data: List<DataCourse>) = with(binding) {
        tvMatematika.setOnClickListener {
            tabPosition = 0
            viewModel.getAchievement(data[tabPosition].id)
            tvMatematika.background = ContextCompat.getDrawable(root.context, R.drawable.bg_card_selected_rounded)
            tvMatematika.setTextColor(ContextCompat.getColor(root.context, R.color.white))
            tvMatematika.setFontBold()
            tvIpa.background = ContextCompat.getDrawable(root.context, R.drawable.bg_card_neutral_rounded)
            tvIpa.setTextColor(ContextCompat.getColor(root.context, R.color.neutral_60))
            tvIpa.setFontRegular()
        }

        tvIpa.setOnClickListener {
            tabPosition = 1
            viewModel.getAchievement(data[tabPosition].id)
            tvMatematika.background = ContextCompat.getDrawable(root.context, R.drawable.bg_card_neutral_rounded)
            tvMatematika.setTextColor(ContextCompat.getColor(root.context, R.color.neutral_60))
            tvMatematika.setFontRegular()
            tvIpa.background = ContextCompat.getDrawable(root.context, R.drawable.bg_card_selected_rounded)
            tvIpa.setTextColor(ContextCompat.getColor(root.context, R.color.white))
            tvIpa.setFontBold()
        }
    }

    private fun setupLoadingSubjectCourse(isLoading: Boolean) = with(binding){
        if (isLoading) {
            clContent.hide()
            shAchievement.show()
        }
    }

    private fun setupLoadingAchievement(isLoading: Boolean) = with(binding){
        if (isLoading) {
            cvAchievement.hide()
            shAchievementContent.show()
            dibEmpty.hide(true)
        } else {
            cvAchievement.show()
            shAchievementContent.hide(true)
        }
    }

    private fun setupError(error: Throwable) {
        binding.shAchievement.hide(true)
        ErrorUtils.setupError(this, error,
            showBottomSheet = {
                DibBottomSheet(ErrorType.ERROR_PROCESS_DATA.name, requireContext()) {
                    setupViewModel()
                }.show(childFragmentManager, ErrorType.ERROR_PROCESS_DATA.name)
            },
            tryAgain = {
                setupViewModel()
            }
        )
    }

    private fun detailSimulation(item: Simulasi) {
        startActivity(Intent(requireContext(), DetailLearningSimulationActivity::class.java).also {
            val data = DataParams(
                item.id,
                item.simulasiUrl,
                EMPTY_STRING,
                EMPTY_STRING
            )
            it.putExtra(DATA, data)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}