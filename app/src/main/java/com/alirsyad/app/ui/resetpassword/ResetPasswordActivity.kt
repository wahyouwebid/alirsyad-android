package com.alirsyad.app.ui.resetpassword

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.model.resetpassword.ResetPasswordRequest
import com.alirsyad.app.data.state.CommonState
import com.alirsyad.app.databinding.ActivityResetPasswordBinding
import com.alirsyad.app.ui.login.LoginActivity
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.isValidPassword
import com.alirsyad.app.utils.isValidPasswordNotMatch
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException

@AndroidEntryPoint
class ResetPasswordActivity : AppCompatActivity() {

    private val binding: ActivityResetPasswordBinding by lazy {
        ActivityResetPasswordBinding.inflate(layoutInflater)
    }

    private val viewModel: ResetPasswordViewModel by viewModels()
    private var uriResetPassword: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViewModel()
        setupListener()
        setupDataIntent()
    }

    private fun setupDataIntent() {
        uriResetPassword = intent.data
    }

    private fun setupViewModel() {
        viewModel.resetPassword.observe(this) {
            when (it) {
                is CommonState.Loading -> {
                    setupLoading(true)
                }
                is CommonState.Result -> {
                    setupLoading(false)
                    onSuccess()
                }
                is CommonState.Error -> {
                    setupLoading(false)
                    setupError(it.error)
                }
            }
        }
    }

    private fun setupListener() = with(binding){
        ivBack.setOnClickListener {
            finish()
        }
        btnReset.setOnClick {
            submitReset()
        }
    }

    private fun submitReset() = with(binding) {
        etPassword.validateForm()
        etConfirmPassword.validateForm()
        if (etPassword.formNotEmpty() && etConfirmPassword.formNotEmpty()) {
            if (uriResetPassword != null) {
                val path = uriResetPassword?.pathSegments
                val token = path?.get(path.size.minus(1))
                val email = uriResetPassword?.getQueryParameter("email")
                val data = ResetPasswordRequest(
                    email = email.toString(),
                    token = token.toString(),
                    password = etPassword.getString(),
                    passwordConfirmation = etConfirmPassword.getString()
                )
                validateDataResetPassword(data)
            }
        }
    }

    private fun validateDataResetPassword(data: ResetPasswordRequest) = with(binding){
        checkDataEntered()
        if (etPassword.getString().isValidPassword()
            && etConfirmPassword.getString().isValidPasswordNotMatch(etPassword.getString())
        ) {
            viewModel.resetPassword(data)
        }
    }

    private fun checkDataEntered() = with(binding){
        if (!etPassword.getString().isValidPassword()) {
            etPassword.setError(getString(R.string.title_error_password_minimum_digits))
        }

        if (!etConfirmPassword.getString().isValidPasswordNotMatch(etPassword.getString())) {
            etConfirmPassword.setError(getString(R.string.title_error_password_not_match))
        }
    }


    private fun onSuccess() = with(binding) {
        startActivity(Intent(this@ResetPasswordActivity, LoginActivity::class.java))
        finish()
    }

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        loading.setLoading(isLoading)
    }

    private fun setupError(throwable: Throwable) {
        try {
            when (throwable) {
                is HttpException -> {
                    if (throwable.response()?.code() == 404) {
                        errorRegister(throwable)
                    } else {
                        DibBottomSheet(ErrorType.ERROR_SYSTEM.name, this)
                            .show(supportFragmentManager, ErrorType.ERROR_SYSTEM.name)
                    }
                }

                is IOException -> {
                    DibBottomSheet(ErrorType.ERROR_CONNECTION.name, this,
                        onClickCancel = {
                            submitReset()
                        },
                        onClickOk = {
                            startActivity(Intent(Settings.ACTION_SETTINGS))
                        }).show(supportFragmentManager, ErrorType.ERROR_CONNECTION.name)
                }

                else -> {
                    DibBottomSheet(ErrorType.ERROR_SYSTEM.name, this,
                        onClickCancel = {
                            submitReset()
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

    private fun errorRegister(throwable: HttpException) = with(binding) {
        val error = ErrorUtils.responseErrorResetPassword(throwable)
        if (error?.errors?.password?.isEmpty() == true && error.errors.password.size <= 1) {
            if (etPassword.getString() == etConfirmPassword.getString()) {
                etPassword.setError(error.errors.password[0])
            } else {
                etConfirmPassword.setError(error.errors.password[0])
            }
        } else {
            error?.errors?.password?.get(0)?.let { etPassword.setError(it) }
            error?.errors?.password?.get(1)?.let { etConfirmPassword.setError(it) }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        onSuccess()
    }
}