package com.alirsyad.app.ui.register

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.model.register.RegisterParams
import com.alirsyad.app.data.state.JenjangState
import com.alirsyad.app.data.state.RegisterState
import com.alirsyad.app.databinding.ActivityRegisterBinding
import com.alirsyad.app.ui.login.LoginActivity
import com.alirsyad.app.utils.Constant.SUCCESS_SENDING_EMAIL
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.Utils.setupSoftMode
import com.alirsyad.app.utils.isValidEmail
import com.alirsyad.app.utils.isValidPassword
import com.alirsyad.app.utils.isValidPasswordNotMatch
import com.alirsyad.app.utils.isValidPhoneNumber
import com.alirsyad.app.utils.show
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException


@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private val binding : ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: RegisterViewModel by viewModels()
    private var jenjangId = 1
    private var isSuccess = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViewModel()
        setupListener()
        setupSoftMode(this)
    }

    private fun setupViewModel() {
        viewModel.getJenjang()
        viewModel.register.observe(this) {
            when (it) {
                is RegisterState.Loading -> {
                    setupLoading(true)
                }
                is RegisterState.Result -> {
                    setupLoading(false)
                    onSuccessRegister()
                }
                is RegisterState.Error -> {
                    setupLoading(false)
                    setupErrorRegister(it.error)
                }
            }
        }

        viewModel.jenjang.observe(this){
            when (it) {
                is JenjangState.Loading -> {
                    setupLoading(true)
                }
                is JenjangState.Result -> {
                    setupLoading(false)
                    successGetDataJenjang(it.data.data)
                }
                is JenjangState.Error -> {
                    setupLoading(false)
                    setupErrorJenjang(it.error)
                }
            }
        }

        viewModel.jenjangSelected.observe(this){ jenjang ->
            if (jenjang != null) {
                binding.etLevel.setText(jenjang.name)
                binding.etLevel.hideError()
                jenjangId = jenjang.id
            }
        }
    }

    private fun onSuccessRegister() = with(binding) {
        dibSuccess.setType(SUCCESS_SENDING_EMAIL)
        dibSuccess.show()
        isSuccess = true
        handlerCloseApp()
    }

    private fun successGetDataJenjang(data: List<com.alirsyad.app.data.model.jenjang.Jenjang>){
        with(binding){
            etLevel.setText(getString(R.string.title_form_hint_level))
            viewModel.jenjangList.postValue(data)
            etLevel.setOnClickListener {
                JenjangBottomSheetFragment().show(
                    this@RegisterActivity.supportFragmentManager, "JENJANG"
                )
            }
        }
    }

    private fun setupLoading(isLoading: Boolean) = with(binding){
        loading.setLoading(isLoading)
    }

    private fun setupListener(){
        with(binding){
            tvLogin.setOnClickListener { finish() }
            etLevel.setOnClickListener {
                viewModel.getJenjang()
                JenjangBottomSheetFragment().show(
                    this@RegisterActivity.supportFragmentManager, "JENJANG"
                )
            }
            btnRegistration.setOnClick {
                submitRegister()
            }
        }
    }

    private fun submitRegister() = with(binding) {
        etName.validateForm()
        etEmail.validateForm()
        etPhone.validateForm()
        etPassword.validateForm()
        etConfirmPassword.validateForm()
        etLevel.validateForm()
        if (etName.formNotEmpty()
            && etEmail.formNotEmpty()
            && etPhone.formNotEmpty()
            && etPassword.formNotEmpty()
            && etConfirmPassword.formNotEmpty()
            && etLevel.formNotEmpty()
        ) {
            validateDataRegister()
        }
    }

    private fun checkDataEntered() = with(binding){
        if (!etEmail.getString().isValidEmail()) {
            etEmail.setError(getString(R.string.title_error_valid_email_address))
        }

        if (!etPhone.getString().isValidPhoneNumber()) {
            etPhone.setError(getString(R.string.title_error_phone_number_minimum_digits))
        }

        if (!etPassword.getString().isValidPassword()) {
            etPassword.setError(getString(R.string.title_error_password_minimum_digits))
        }

        if (!etConfirmPassword.getString().isValidPasswordNotMatch(etPassword.getString())) {
            etConfirmPassword.setError(getString(R.string.title_error_password_not_match))
        }
    }

    private fun validateDataRegister() = with(binding) {
        checkDataEntered()
        if (etEmail.getString().isValidEmail()
            && etPhone.getString().isValidPhoneNumber()
            && etPassword.getString().isValidPassword()
            && etConfirmPassword.getString().isValidPasswordNotMatch(etPassword.getString())
        ) {
            viewModel.register(
                RegisterParams(
                    etName.getString(),
                    etEmail.getString(),
                    etPhone.getString(),
                    etPassword.getString(),
                    etConfirmPassword.getString(),
                    jenjangId,
                )
            )
        }
    }

    private fun setupErrorJenjang(error: Throwable) {
        ErrorUtils.setupError(this, error,
            tryAgain = {
                viewModel.getJenjang()
            },
            showBottomSheet = {
                DibBottomSheet(ErrorType.ERROR_PROCESS_DATA.name, this, onClickOk = {
                    viewModel.getJenjang()
                }).show(supportFragmentManager, ErrorType.ERROR_PROCESS_DATA.name)
            }
        )
    }

    private fun setupErrorRegister(throwable: Throwable) {
        try {
            when (throwable) {
                is HttpException -> {
                    if (throwable.response()?.code() == 404) {
                        errorRegister(throwable)
                        DibBottomSheet(ErrorType.ERROR_REGISTRATION.name, this@RegisterActivity)
                            .show(supportFragmentManager, ErrorType.ERROR_REGISTRATION.name)
                    } else {
                        DibBottomSheet(ErrorType.ERROR_SYSTEM.name, this@RegisterActivity)
                            .show(supportFragmentManager, ErrorType.ERROR_SYSTEM.name)
                    }
                }

                is IOException -> {
                    DibBottomSheet(ErrorType.ERROR_CONNECTION.name, this@RegisterActivity,
                    onClickCancel = {
                        submitRegister()
                    },
                    onClickOk = {
                        startActivity(Intent(Settings.ACTION_SETTINGS))
                    }).show(supportFragmentManager, ErrorType.ERROR_CONNECTION.name)
                }

                else -> {
                    DibBottomSheet(ErrorType.ERROR_SYSTEM.name, this@RegisterActivity,
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

    private fun errorRegister(throwable: HttpException) = with(binding) {
        val error = ErrorUtils.responseErrorRegister(throwable)
        if (error?.data?.email?.isNotEmpty() == true) {
            etEmail.setError(error.data.email[0])
        }
        if (error?.data?.phone?.isNotEmpty() == true) {
            etPhone.setError(error.data.phone[0])
        }

        if (error?.data?.password?.isNotEmpty() == true && error.data.password.size <= 1) {
            if (etPassword.getString() == etConfirmPassword.getString()) {
                etPassword.setError(error.data.password[0])
            } else {
                etConfirmPassword.setError(error.data.password[0])
            }
        } else {
            error?.data?.password?.get(0)?.let { etPassword.setError(it) }
            error?.data?.password?.get(1)?.let { etConfirmPassword.setError(it) }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isSuccess) {
            handlerCloseApp()
        }
    }

    private fun handlerCloseApp() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 3000)
    }
}