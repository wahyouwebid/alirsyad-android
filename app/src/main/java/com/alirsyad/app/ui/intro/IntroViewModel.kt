package com.alirsyad.app.ui.intro

import androidx.lifecycle.ViewModel
import com.alirsyad.app.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun setDataIntro(intro : Boolean){
        repository.setIntro(intro)
    }
}