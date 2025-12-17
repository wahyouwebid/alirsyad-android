package com.alirsyad.app.ui.changedpassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alirsyad.app.data.repository.Repository
import com.alirsyad.app.data.state.UpdateProfileState
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val updatePassword: MutableLiveData<UpdateProfileState> by lazy {
        MutableLiveData()
    }

    fun updatePassword(
        oldPassword : String,
        newPassword : String,
        confirmPassword : String
    ){
        val json = JSONObject().also {
            it.put("old_password", oldPassword)
            it.put("new_password", newPassword)
            it.put("new_password_confirmation", confirmPassword)
        }
        val request = JsonParser.parseString(json.toString()).asJsonObject
        repository.updatePassword(
            repository.getToken(),
            request,
            updatePassword,
        )
    }

    fun getIsPengunjung() = repository.getIsPengunjung()

    fun setIsLogin() = repository.setIsLogin(false)
}