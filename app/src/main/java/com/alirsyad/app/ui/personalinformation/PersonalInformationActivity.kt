package com.alirsyad.app.ui.personalinformation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.model.profile.DataProfile
import com.alirsyad.app.data.state.ProfileState
import com.alirsyad.app.databinding.ActivityPersonalInformationBinding
import com.alirsyad.app.ui.changedpassword.ChangedPasswordActivity
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.Utils.setupStatusBar
import com.alirsyad.app.utils.checkEmpty
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalInformationActivity : AppCompatActivity() {

    private val binding: ActivityPersonalInformationBinding by lazy {
        ActivityPersonalInformationBinding.inflate(layoutInflater)
    }

    private val viewModel: PersonalInformationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar()
        setupViewModel()
        setupStatusBar(this)
        setupRole()
        setupListener()
    }

    private fun setupToolbar() = with(binding) {
        toolbar.setToolbar(getString(R.string.title_personal_information)){
            finish()
        }
    }

    private fun setupViewModel() {
        viewModel.getProfile()
        viewModel.profile.observe(this) {
            when (it) {
                is ProfileState.Loading -> {
                    setupLoading(true)
                }
                is ProfileState.Result -> {
                    setupLoading(false)
                    setData(it.data.data)
                }
                is ProfileState.Error -> {
                    setupLoading(false)
                    setupErrorGetProfile(it.error)
                }
            }
        }
    }

    private fun setupRole() = with(binding) {
        when(viewModel.getIsPengunjung()) {
            0 -> dibNis.setHeader(getString(R.string.title_form_nis))
            1 -> dibNis.setHeader(getString(R.string.title_email))
        }
    }

    private fun setData(data : DataProfile) {
        with(binding) {
            when(viewModel.getIsPengunjung()) {
                0 -> {
                    dibNis.setText(data.nis)
                    dibFullName.setText(data.name)
                    dibPassword.setText(String.format("********"))
                    dibActiveClass.setText(String.format(
                        "${data.jenjang} - Kelas ${data.tingkat.checkEmpty() + " " + data.kelas.checkEmpty()}"
                    ))
                }

                1 -> {
                    dibNis.setText(data.email)
                    dibFullName.setText(data.name)
                    dibPassword.setText(String.format("********"))
                    if (data.tingkat.isNullOrEmpty() || data.kelas.isNullOrEmpty()) {
                        dibActiveClass.hide(true)
                    } else {
                        dibActiveClass.setText(String.format(
                            "${data.jenjang} - Kelas ${data.tingkat.checkEmpty() + " " + data.kelas.checkEmpty()}"
                        ))
                    }
                }
            }
        }
    }

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        if (isLoading) loading.show() else loading.hide(true)
    }

    private fun setupErrorGetProfile(error: Throwable) {
        ErrorUtils.setupError(this, error,
            showBottomSheet = {
                DibBottomSheet(ErrorType.ERROR_PROCESS_DATA.name, this, onClickOk = {
                    viewModel.getProfile()
                }).show(supportFragmentManager, ErrorType.ERROR_PROCESS_DATA.name)
            },
            tryAgain = {
                viewModel.getProfile()
            }
        )
    }

    private fun setupListener() = with(binding){
        dibPassword.setOnClickListener {
            startActivity(Intent(this@PersonalInformationActivity, ChangedPasswordActivity::class.java))
        }
    }
}