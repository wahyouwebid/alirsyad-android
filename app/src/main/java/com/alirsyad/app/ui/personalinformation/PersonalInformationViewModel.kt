package com.alirsyad.app.ui.personalinformation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alirsyad.app.data.repository.Repository
import com.alirsyad.app.data.state.ProfileState
import com.alirsyad.app.data.state.UpdateProfileState
import com.alirsyad.app.data.state.UploadPhotoState
import com.alirsyad.app.utils.toMultiPartBody
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PersonalInformationViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val profile: MutableLiveData<ProfileState> by lazy {
        MutableLiveData()
    }

    val updateProfile: MutableLiveData<UpdateProfileState> by lazy {
        MutableLiveData()
    }

    val updatePassword: MutableLiveData<UpdateProfileState> by lazy {
        MutableLiveData()
    }

    val uploadPhoto: MutableLiveData<UploadPhotoState> by lazy {
        MutableLiveData()
    }

    fun getProfile(){
        repository.getProfile(repository.getToken(), profile)
    }

    fun updateEmail(email : String){
        val json = JSONObject().also {
            it.put("email", email)
        }
        val request = JsonParser.parseString(json.toString()).asJsonObject
        repository.updateProfile(
            repository.getToken(),
            request,
            updateProfile,
        )
    }

    fun updatePhoto(photo : String){
        val json = JSONObject().also {
            it.put("photo", photo)
        }
        val request = JsonParser.parseString(json.toString()).asJsonObject
        repository.updateProfile(
            repository.getToken(),
            request,
            updateProfile,
        )
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

    fun uploadPhoto(
        file: File
    ){
        repository.uploadPhoto(
            repository.getToken(),
            file.toMultiPartBody("file"),
            uploadPhoto,
        )
    }

    fun getName(): String {
        return repository.getName()
    }

    fun updateSessionEmail(email: String) {
        repository.updateEmail(email)
    }

    fun updateSessionPhoto(photo: String){
        repository.updatePhoto(photo)
    }

    fun getEmail(): String {
        return repository.getEmail()
    }

    fun getPhoto(): String {
        return repository.getPhoto()
    }

    fun getNis(): String {
        return repository.getNis()
    }

    fun getKelas(): String {
        return repository.getKelas()
    }

    fun getTingkat(): String {
        return repository.getSubjectClass()
    }

    fun logOut(){
        repository.logOut()
    }

    fun getIsPengunjung() : Int {
        return repository.getIsPengunjung()
    }
}