package com.alirsyad.app.ui.forgotpassword

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.state.CommonState
import com.alirsyad.app.databinding.ActivityForgotPasswordBinding
import com.alirsyad.app.ui.login.LoginActivity
import com.alirsyad.app.utils.Constant.SUCCESS_NIS_FORGOT_PASSWORD
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.show
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException

@AndroidEntryPoint
class ForgotPasswordStudentActivity : AppCompatActivity() {

    private val binding: ActivityForgotPasswordBinding by lazy {
        ActivityForgotPasswordBinding.inflate(layoutInflater)
    }

    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViewModel()
        setupListener()
    }

    private fun setupViewModel() {
        viewModel.forgotPassword.observe(this) {
            when (it) {
                is CommonState.Loading -> {
                    setupLoading(true)
                }
                is CommonState.Result -> {
                    setupLoading(false)
                    onSuccessLogin()
                }
                is CommonState.Error -> {
                    setupLoading(false)
                    setupError(it.error)
                }
            }
        }
    }

    private fun setupListener() = with(binding){
        ivBack.setOnClickListener { finish() }
        btnForgot.setOnClick {
            etNis.validateForm()
            if (etNis.formNotEmpty()) {
                viewModel.forgotPasswordStudent(etNis.getString())
            }
        }
    }

    private fun onSuccessLogin() = with(binding) {
        dibSuccess.show()
        dibSuccess.setType(SUCCESS_NIS_FORGOT_PASSWORD)
        btnForgot.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@ForgotPasswordStudentActivity, LoginActivity::class.java))
            finish()
        }, 3000)
    }

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        loading.setLoading(isLoading)
        if (isLoading) {
            dibSuccess.hide(true)
        }
    }

    private fun setupError(throwable: Throwable) {
        try {
            when (throwable) {
                is HttpException -> {
                    if (throwable.response()?.code() == 404) {
                        setInlineError(throwable)
                    } else {
                        DibBottomSheet(ErrorType.ERROR_SYSTEM.name, this@ForgotPasswordStudentActivity)
                            .show(supportFragmentManager, ErrorType.ERROR_SYSTEM.name)
                    }
                }

                is IOException -> {
                    DibBottomSheet(ErrorType.ERROR_CONNECTION.name, this@ForgotPasswordStudentActivity)
                        .show(supportFragmentManager, ErrorType.ERROR_CONNECTION.name)
                }

                else -> {
                    DibBottomSheet(ErrorType.ERROR_SYSTEM.name, this@ForgotPasswordStudentActivity)
                        .show(supportFragmentManager, ErrorType.ERROR_SYSTEM.name)
                }
            }
        } catch (e: Exception) {
            Log.e("FORGOT PASSWORD", e.message.toString())
        }
    }

    private fun setInlineError(throwable: HttpException) = with(binding){
        val error = ErrorUtils.responseErrorForgotNis(throwable)
        if (error?.message?.isNotEmpty() == true) {
            etNis.setError(error.message)
        }
    }
}