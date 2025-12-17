package com.alirsyad.app.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alirsyad.app.R
import com.alirsyad.app.data.state.LoginState
import com.alirsyad.app.databinding.FragmentLoginStudentBinding
import com.alirsyad.app.ui.MainActivity
import com.alirsyad.app.ui.forgotpassword.ForgotPasswordStudentActivity
import com.alirsyad.app.ui.register.RegisterActivity
import com.alirsyad.app.utils.Constant
import com.alirsyad.app.utils.Constant.STUDENT
import com.alirsyad.app.utils.ErrorUtils
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.isValidPassword
import com.alirsyad.app.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginStudentFragment(val activity: LoginActivity) : Fragment() {

    private val binding: FragmentLoginStudentBinding by lazy {
        FragmentLoginStudentBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupListener()
    }

    private fun setupViewModel() {
        viewModel.login.observe(viewLifecycleOwner) {
            when (it) {
                is LoginState.Loading -> {
                    setupLoading(true)
                }
                is LoginState.Result -> {
                    setupLoading(false)
                    onSuccessLogin()
                    setDataUser(it.data.data)
                }
                is LoginState.Error -> {
                    setupLoading(false)
                    setupError(it.error)
                }
            }
        }
    }

    private fun setDataUser(data: com.alirsyad.app.data.model.user.DataUser) {
        viewModel.setDataUser(data)
    }

    private fun onSuccessLogin() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        activity.finish()
    }

    private fun setupListener() {
        with(binding) {
            viewModel.setClassPosition()

            btnLogin.setOnClick {
                etNis.validateForm()
                etPassword.validateForm()
                if (etNis.formNotEmpty() && etPassword.formNotEmpty()) {
                    tvLoginError.hide(true)
                    validateDataRegister()
                }
            }

            tvForgotPassword.setOnClickListener {
                startActivity(
                    Intent(requireContext(), ForgotPasswordStudentActivity::class.java).also {
                        it.putExtra(Constant.USER_TYPE, STUDENT)
                    }
                )
            }

            btnRegister.setOnClick {
                startActivity(Intent(requireContext(), RegisterActivity::class.java))
            }
        }
    }

    private fun validateDataRegister() = with(binding) {
        checkDataEntered()
        if (etPassword.getString().isValidPassword()) {
            viewModel.login(
                etNis.getString(),
                etPassword.getString(),
                Constant.Role.STUDENT
            )
        }
    }

    private fun checkDataEntered() = with(binding){
        if (!etPassword.getString().isValidPassword()) {
            etPassword.setError(getString(R.string.title_error_password_minimum_digits))
        }
    }

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        loading.setLoading(isLoading)
    }

    private fun setupError(error: Throwable) = with(binding) {
        ErrorUtils.setupError(this@LoginStudentFragment, error,
            tryAgain = {
                etNis.validateForm()
                etPassword.validateForm()
                if (etNis.formNotEmpty() && etPassword.formNotEmpty()) {
                    tvLoginError.hide(true)
                    validateDataRegister()
                }
            },
            showBottomSheet = {
                tvLoginError.show()
                tvLoginError.text = getString(R.string.title_error_login_failed_message_student)
            }
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}