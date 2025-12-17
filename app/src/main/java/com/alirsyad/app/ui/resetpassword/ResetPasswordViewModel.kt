package com.alirsyad.app.ui.resetpassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alirsyad.app.data.model.resetpassword.ResetPasswordRequest
import com.alirsyad.app.data.repository.Repository
import com.alirsyad.app.data.state.CommonState
import com.google.gson.Gson
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val resetPassword: MutableLiveData<CommonState> by lazy {
        MutableLiveData()
    }

    fun resetPassword(request : ResetPasswordRequest){
        val body = JsonParser.parseString(Gson().toJson(request)).asJsonObject
        repository.resetPassword(body, resetPassword)
    }
}