package com.alirsyad.app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alirsyad.app.databinding.ActivitySplashBinding
import com.alirsyad.app.ui.MainActivity
import com.alirsyad.app.ui.intro.IntroActivity
import com.alirsyad.app.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupStatusBar()
        moveActivity()
        setupViewModel()
    }

    private fun setupStatusBar() {
        with(window) {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    private fun setupViewModel() {
        viewModel.setClassPosition()
    }

    private fun moveActivity() {
        Handler(mainLooper).postDelayed({
            if (viewModel.isIntro()){
                checkIsLogin()
            } else {
                goToIntro()
            }
        }, 2000)
    }
    
    private fun checkIsLogin() {
        if (viewModel.isLogin()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun goToIntro() {
        startActivity(Intent(this, IntroActivity::class.java))
        finish()
    }
}