package com.alirsyad.app.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alirsyad.app.R
import com.alirsyad.app.databinding.ActivityLoginBinding
import com.alirsyad.app.ui.adapter.LoginViewPagerAdapter
import com.alirsyad.app.utils.Constant.ROLE
import com.alirsyad.app.utils.Constant.STUDENT
import com.alirsyad.app.utils.Constant.VISITOR
import com.alirsyad.app.utils.Utils.setupSoftMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModels()
    private var userType = STUDENT

    private val adapter: LoginViewPagerAdapter by lazy {
        LoginViewPagerAdapter(
            listOf(
                LoginStudentFragment(this),
                LoginVisitorFragment(this)
            ), this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListener()
        setupSoftMode(this)
    }

    private fun setupListener() {
        with(binding) {
            viewModel.setClassPosition()
            dibTab.setupTab(
                tabStudentSelected = {
                    setStudentState()
                },
                tabVisitorSelected = {
                    setVisitorState()
                }
            )

            vpLogin.also {
                it.adapter = adapter
            }

            vpLogin.isUserInputEnabled = false


            if (role == 0) {
                setStudentState()
                dibTab.setTabStudent()
            } else {
                setVisitorState()
                dibTab.setTabVisitor()
            }
        }
    }

    private fun setStudentState() = with(binding) {
        userType = STUDENT
        tvSubtitleLoginType.text = getString(R.string.title_subtitle_login_student)
        vpLogin.setCurrentItem(0, false)
    }

    private fun setVisitorState() = with(binding) {
        userType = VISITOR
        tvSubtitleLoginType.text = getString(R.string.title_subtitle_login_visitor)
        vpLogin.setCurrentItem(1, false)
    }

    val role: Int? by lazy {
        intent.getIntExtra(ROLE, 0)
    }
}