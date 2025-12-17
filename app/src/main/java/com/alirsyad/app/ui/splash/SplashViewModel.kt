package com.alirsyad.app.ui.splash

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: com.alirsyad.app.data.repository.Repository
) : ViewModel() {

    fun isLogin(): Boolean {
        return repository.getIsLogin()
    }

    fun setClassPosition() {
        repository.setIsTabClass(false)
    }

    fun isIntro(): Boolean {
        return repository.getIsIntro()
    }

    fun setClassPosition(position: Int) {
        repository.setPosition(position)
    }
}