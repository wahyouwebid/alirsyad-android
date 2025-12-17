package com.alirsyad.app.ui.subjectsummary

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.model.params.DataParams
import com.alirsyad.app.data.model.summaryoflevel.SummaryOfLevel
import com.alirsyad.app.data.model.toolbar.DataToolbar
import com.alirsyad.app.data.state.SummaryOfLevelState
import com.alirsyad.app.databinding.ActivitySubjectSummaryBinding
import com.alirsyad.app.ui.learning.learningadaptive.LearningAdaptiveActivity
import com.alirsyad.app.ui.learning.learningmodule.LearningModuleActivity
import com.alirsyad.app.ui.learning.learningsimulation.LearningSimulationActivity
import com.alirsyad.app.ui.learning.learningvideo.LearningVideoActivity
import com.alirsyad.app.utils.Constant.Intent.DATA
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.Utils.setupStatusBar
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubjectSummaryActivity : AppCompatActivity() {

    private val binding: ActivitySubjectSummaryBinding by lazy {
        ActivitySubjectSummaryBinding.inflate(layoutInflater)
    }

    private val viewModel: SubjectSummaryViewModel by viewModels()

    private val data: DataParams? by lazy { intent.getParcelableExtra("data") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setToolbar()
        setupStatusBar(this)
        setupListener()
        setupObserveViewModel()
    }

    private fun setupViewModel() {
        data?.id?.let { viewModel.getSummaryOfLevel(it) }
    }

    private fun setupObserveViewModel() {
        viewModel.summaryOfLevel.observe(this) {
            when(it) {
                is SummaryOfLevelState.Loading -> {}
                is SummaryOfLevelState.Result -> setData(it.data.data)
                is SummaryOfLevelState.Error -> setupErrorSubjectCourse(it.error)
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
                    data?.jenjang
                )
            ) {
                finish()
            }
        }
    }

    private fun setData(dataSummary: List<SummaryOfLevel>) {
        with(binding) {
            if (dataSummary.size >= 3) {
                val easyPercentage = dataSummary[0].percentage!!
                val mediumPercentage = dataSummary[1].percentage!!
                val hardPercentage = dataSummary[2].percentage!!

                val easyQuestion = "${dataSummary[0].totalBenar}/${dataSummary[0].totalTerjawab}"
                val mediumQuestion = "${dataSummary[1].totalBenar}/${dataSummary[1].totalTerjawab}"
                val hardQuestion = "${dataSummary[2].totalBenar}/${dataSummary[2].totalTerjawab}"

                pbEasy.progress = easyPercentage
                pbMedium.progress = mediumPercentage
                pbHard.progress = hardPercentage
                tvEasyQuestions.text = easyQuestion
                tvMediumQuestions.text = mediumQuestion
                tvHardQuestions.text = hardQuestion
            }

            when(data?.name) {
                "Tematik",
                "Matematika",
                "IPA",
                "Pelajaran TK B",
                "Pelajaran TK A" -> clLearningSimulation.show()
                else -> clLearningSimulation.hide(true)
            }
        }
    }

    private fun setupListener() {
        with(binding) {
            clLearningModule.setOnClickListener {
                startActivity(
                    Intent(
                        this@SubjectSummaryActivity,
                        LearningModuleActivity::class.java
                    ).also {
                        val data = DataParams(
                            data?.id,
                            data?.name,
                            data?.tingkat,
                            data?.jenjang
                        )
                        it.putExtra(DATA, data)
                    }
                )
            }

            clLearningSimulation.setOnClickListener {
                startActivity(
                    Intent(
                        this@SubjectSummaryActivity,
                        LearningSimulationActivity::class.java
                    ).also {
                        val data = DataParams(
                            data?.id,
                            data?.name,
                            data?.tingkat,
                            data?.jenjang
                        )
                        it.putExtra(DATA, data)
                    }
                )
            }

            clLearningVideo.setOnClickListener {
                startActivity(
                    Intent(
                        this@SubjectSummaryActivity,
                        LearningVideoActivity::class.java
                    ).also {
                        val data = DataParams(
                            data?.id,
                            data?.name,
                            data?.tingkat,
                            data?.jenjang
                        )
                        it.putExtra(DATA, data)

                    }
                )
            }

            clLearningAdaptive.setOnClickListener {
                startActivity(
                    Intent(
                        this@SubjectSummaryActivity,
                        LearningAdaptiveActivity::class.java
                    ).also {
                        val data = DataParams(
                            data?.id,
                            data?.name,
                            data?.tingkat,
                            data?.jenjang
                        )
                        it.putExtra(DATA, data)

                    }
                )
            }
        }
    }

    private fun setupErrorSubjectCourse(error: Throwable) {
        ErrorUtils.setupError(this, error,
            showBottomSheet = {
                DibBottomSheet(ErrorType.ERROR_PROCESS_DATA.name, this)
                    .show(this.supportFragmentManager, ErrorType.ERROR_PROCESS_DATA.name)
            },
            tryAgain = {
                setupViewModel()
            }
        )
    }

    override fun onResume() {
        super.onResume()
        setupViewModel()
    }
}