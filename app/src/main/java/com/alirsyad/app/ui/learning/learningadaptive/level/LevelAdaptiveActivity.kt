package com.alirsyad.app.ui.learning.learningadaptive.level

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.model.params.DataParams
import com.alirsyad.app.data.model.toolbar.DataToolbar
import com.alirsyad.app.data.state.LevelState
import com.alirsyad.app.databinding.ActivityLevelAdaptiveBinding
import com.alirsyad.app.ui.learning.learningadaptive.question.QuestionAdaptiveActivity
import com.alirsyad.app.utils.Constant
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.Utils.setupStatusBar
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelAdaptiveActivity : AppCompatActivity() {

    private val binding : ActivityLevelAdaptiveBinding by lazy {
        ActivityLevelAdaptiveBinding.inflate(layoutInflater)
    }

    private val viewModel : LevelAdaptiveViewModel by viewModels()

    private var data : DataParams? = null
    private var id : Int? = null
    private var name : String? = null
    private var mataPelajaranId : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupStatusBar(this)
        setupToolbar()
        setupDataIntent()
        setupContent()
    }

    override fun onResume() {
        super.onResume()
        setupViewModel()
    }

    private fun setupToolbar() {
        with(binding) {
            toolbar.setToolbar(
                getString(R.string.title_form_level),
                DataToolbar(
                    viewModel.getIsPengunjung(),
                    viewModel.getTingkat(),
                    viewModel.getKelas()
                )
            ) {
                finish()
            }
        }
    }

    private fun setupDataIntent() {
        data = intent.getParcelableExtra(Constant.Intent.DATA)
        id = intent.getIntExtra(Constant.Intent.ID, 0)
        name =  intent.getStringExtra(Constant.Intent.NAME)
        mataPelajaranId =  intent.getIntExtra(Constant.Intent.MATA_PELAJARAN_ID, 0)
    }

    private fun setupViewModel(){
        viewModel.getData(mataPelajaranId ?: 0)
    }

    private fun setupContent() {
        with(binding) {
            viewModel.level.observe(this@LevelAdaptiveActivity) { state ->
                when(state) {
                    is LevelState.Loading -> {
                        llContent.hide()
                        loading.setLoading(true)
                    }
                    is LevelState.Result -> {
                        loading.setLoading(false)
                        if (state.data.data.isNotEmpty()) {
                            val items = state.data.data
                            val easy = items.first()
                            val medium = items[1]
                            val hard = items[2]

                            if (state.data.data.any { it.isAvailable }) {
                                if (easy.isAvailable) {
                                    clEasy.show()
                                    llContent.show()
                                    clEasy.setOnClickListener {
                                        goToQuestion(easy.paketSoalId)
                                    }
                                }

                                if (medium.isAvailable) {
                                    clMedium.show()
                                    llContent.show()
                                    clMedium.setOnClickListener {
                                        goToQuestion(medium.paketSoalId)
                                    }
                                }

                                if (hard.isAvailable) {
                                    clHard.show()
                                    llContent.show()
                                    clHard.setOnClickListener {
                                        goToQuestion(hard.paketSoalId)
                                    }
                                }
                            } else {
                                clEasy.show()
                                llContent.show()
                                viewDividerEasy.hide()
                                clEasy.setOnClickListener {
                                    goToQuestion(easy.paketSoalId)
                                }
                            }
                        }
                    }
                    is LevelState.Error -> {
                        llContent.hide()
                        loading.setLoading(false)
                        setupError(state.error)
                    }
                }
            }
        }
    }

    private fun goToQuestion(paketSoalId: Int) {
        val params = DataParams(
            paketSoalId,
            data?.name,
            data?.tingkat,
            data?.jenjang
        )
        startActivity(
            android.content.Intent(
                this@LevelAdaptiveActivity,
                QuestionAdaptiveActivity::class.java
            ).also {
                it.putExtra(Constant.Intent.DATA, params)
                it.putExtra(Constant.Intent.ID, paketSoalId)
                it.putExtra(Constant.Intent.NAME, name)
                it.putExtra(Constant.Intent.MATA_PELAJARAN_ID, mataPelajaranId)
            }
        )
    }

    private fun setupError(error: Throwable) = with(binding) {
        loading.setLoading(false)
        ErrorUtils.setupError(this@LevelAdaptiveActivity, error,
            showBottomSheet = {
                DibBottomSheet(ErrorType.ERROR_PROCESS_DATA.name, this@LevelAdaptiveActivity, onClickOk = {
                    finish()
                }).show(supportFragmentManager, ErrorType.ERROR_PROCESS_DATA.name)
            },
            tryAgain = {
                viewModel.getData(mataPelajaranId ?: 0)
            }
        )
    }

}