package com.alirsyad.app.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alirsyad.app.data.model.user.DataUser
import com.alirsyad.app.data.repository.Repository
import com.alirsyad.app.data.state.CommonState
import com.alirsyad.app.data.state.LoginState
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val login: MutableLiveData<LoginState> by lazy {
        MutableLiveData()
    }

    val forgotPassword: MutableLiveData<CommonState> by lazy {
        MutableLiveData()
    }

    fun login(nis: String, password: String, role: String) {
        val json = JSONObject()
        json.put("nis", nis)
        json.put("password", password)
        json.put("role", role)
        val body = JsonParser.parseString(json.toString()).asJsonObject

        repository.login(body, login)
    }

    fun setDataUser(data: DataUser) {
        repository.setDataUser(data)
    }

    fun forgotPassword(email : String){
        val json = JSONObject()
        json.put("email", email)
        val body = JsonParser.parseString(json.toString()).asJsonObject

        repository.forgotPassword(body, forgotPassword)
    }

    fun setClassPosition() {
        repository.setIsTabClass(false)
    }
}