package com.alirsyad.app.ui.forgotpassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alirsyad.app.data.repository.Repository
import com.alirsyad.app.data.state.CommonState
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val forgotPassword: MutableLiveData<CommonState> by lazy {
        MutableLiveData()
    }

    fun forgotPasswordVisitor(email : String){
        val json = JSONObject()
        json.put("email", email)
        val body = JsonParser.parseString(json.toString()).asJsonObject

        repository.forgotPassword(body, forgotPassword)
    }

    fun forgotPasswordStudent(email : String){
        val json = JSONObject()
        json.put("nis", email)
        val body = JsonParser.parseString(json.toString()).asJsonObject

        repository.forgotPasswordStudent(body, forgotPassword)
    }
}