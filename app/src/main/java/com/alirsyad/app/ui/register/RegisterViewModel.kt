package com.alirsyad.app.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alirsyad.app.data.model.jenjang.Jenjang
import com.alirsyad.app.data.model.register.RegisterParams
import com.alirsyad.app.data.state.JenjangState
import com.alirsyad.app.data.state.RegisterState
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: com.alirsyad.app.data.repository.Repository
) : ViewModel() {

    val jenjang: MutableLiveData<JenjangState> by lazy {
        MutableLiveData()
    }

    val register: MutableLiveData<RegisterState> by lazy {
        MutableLiveData()
    }

    val jenjangList : MutableLiveData<List<Jenjang>> by lazy {
        MutableLiveData()
    }

    val jenjangSelected : MutableLiveData<Jenjang> by lazy {
        MutableLiveData()
    }

    fun register(params: RegisterParams) {
        val json = JSONObject()
        json.put("name", params.name)
        json.put("email", params.email)
        json.put("phone", params.phone)
        json.put("password", params.password)
        json.put("password_confirmation", params.passwordConfirmation)
        json.put("jenjang_id", params.jenjangId)
        val body = JsonParser.parseString(json.toString()).asJsonObject

        repository.register(body, register)
    }

    fun getJenjang(){
        repository.getListJenjang(jenjang)
    }
}