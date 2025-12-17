package com.alirsyad.app.ui.changedpassword

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.state.UpdateProfileState
import com.alirsyad.app.databinding.ActivityChangedPasswordBinding
import com.alirsyad.app.ui.login.LoginActivity
import com.alirsyad.app.utils.Constant.ROLE
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.Utils.setupStatusBar
import com.alirsyad.app.utils.isValidPassword
import com.alirsyad.app.utils.isValidPasswordNotMatch
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException

@AndroidEntryPoint
class ChangedPasswordActivity : AppCompatActivity() {

    private val binding: ActivityChangedPasswordBinding by lazy {
        ActivityChangedPasswordBinding.inflate(layoutInflater)
    }

    private val viewModel: ChangePasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar()
        setupViewModel()
        setupStatusBar(this)
        setupSoftMode(this)
    }

    private fun setupToolbar() = with(binding) {
        toolbar.setToolbar(getString(R.string.title_change_password)){
            finish()
        }
    }

    private fun setupViewModel() {
        binding.btnSave.setOnClick { submitRegister() }
        viewModel.updatePassword.observe(this) {
            when (it) {
                is UpdateProfileState.Loading -> {
                    setupLoading(true)
                }
                is UpdateProfileState.Result -> {
                    setupLoading(false)
                    successChangePassword()
                }
                is UpdateProfileState.Error -> {
                    setupLoading(false)
                    setupError(it.error)
                }
            }
        }
    }

    private fun successChangePassword() {
        viewModel.setIsLogin()
        val i = Intent(this, LoginActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        i.putExtra(ROLE, viewModel.getIsPengunjung())
        startActivity(i)
    }

    private fun submitRegister() = with(binding) {
        etPrevPassword.validateForm()
        etNewPassword.validateForm()
        etConfirmNewPassword.validateForm()
        if (etPrevPassword.formNotEmpty()
            && etNewPassword.formNotEmpty()
            && etConfirmNewPassword.formNotEmpty()
        ) {
            validateChangePassword()
        }
    }

    private fun validateChangePassword() = with(binding) {
        checkDataEntered()
        if (etPrevPassword.getString().isValidPassword()
            && etNewPassword.getString().isValidPassword()
            && etConfirmNewPassword.getString().isValidPasswordNotMatch(etNewPassword.getString())
        ) {
            viewModel.updatePassword(
                etPrevPassword.getString(),
                etNewPassword.getString(),
                etConfirmNewPassword.getString(),
            )
        }
    }

    private fun checkDataEntered() = with(binding){
        if (!etPrevPassword.getString().isValidPassword()) {
            etPrevPassword.setError(getString(R.string.title_error_password_minimum_digits))
        }

        if (!etNewPassword.getString().isValidPassword()) {
            etNewPassword.setError(getString(R.string.title_error_password_minimum_digits))
        }

        if (!etConfirmNewPassword.getString().isValidPasswordNotMatch(etNewPassword.getString())) {
            etConfirmNewPassword.setError(getString(R.string.title_error_password_not_match))
        }
    }

    private fun setupLoading(isLoading: Boolean) = with(binding){
        loading.setLoading(isLoading)
    }

    private fun setupError(throwable: Throwable) {
        try {
            when (throwable) {
                is HttpException -> {
                    if (throwable.response()?.code() == 404) {
                        errorPassword(throwable)
                        DibBottomSheet(ErrorType.ERROR_REGISTRATION.name, this)
                            .show(supportFragmentManager, ErrorType.ERROR_REGISTRATION.name)
                    } else {
                        DibBottomSheet(ErrorType.ERROR_SYSTEM.name, this)
                            .show(supportFragmentManager, ErrorType.ERROR_SYSTEM.name)
                    }
                }

                is IOException -> {
                    DibBottomSheet(ErrorType.ERROR_CONNECTION.name, this,
                        onClickCancel = {
                            submitRegister()
                        },
                        onClickOk = {
                            startActivity(Intent(Settings.ACTION_SETTINGS))
                        }).show(supportFragmentManager, ErrorType.ERROR_CONNECTION.name)
                }

                else -> {
                    DibBottomSheet(ErrorType.ERROR_SYSTEM.name, this,
                        onClickCancel = {
                            submitRegister()
                        },
                        onClickOk = {
                            startActivity(Intent(Settings.ACTION_SETTINGS))
                        }
                    ).show(supportFragmentManager, ErrorType.ERROR_SYSTEM.name)
                }
            }
        } catch (e: Exception) {
            Log.e("REGISTER", e.message.toString())
        }
    }

    private fun errorPassword(throwable: HttpException) = with(binding) {
        val error = ErrorUtils.responseErrorRegister(throwable)

        if (error?.data?.password?.isNotEmpty() == true && error.data.password.size <= 1) {
            if (etNewPassword.getString() == etConfirmNewPassword.getString()) {
                etNewPassword.setError(error.data.password[0])
            } else {
                etConfirmNewPassword.setError(error.data.password[0])
            }
        } else {
            error?.data?.password?.get(0)?.let { etNewPassword.setError(it) }
            error?.data?.password?.get(1)?.let { etConfirmNewPassword.setError(it) }
        }
    }

    private fun setupSoftMode(activity: AppCompatActivity) {
        activity.window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        )
    }
}