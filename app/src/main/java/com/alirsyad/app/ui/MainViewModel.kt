package com.alirsyad.app.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alirsyad.app.data.repository.Repository
import com.alirsyad.app.data.state.AchievementSimulationState
import com.alirsyad.app.data.state.BannerState
import com.alirsyad.app.data.state.CourseState
import com.alirsyad.app.data.state.EReportAchievementState
import com.alirsyad.app.data.state.EReportChartState
import com.alirsyad.app.data.state.EReportCurrentScoreState
import com.alirsyad.app.data.state.ProfileState
import com.alirsyad.app.data.state.TingkatState
import com.alirsyad.app.data.state.UpdateProfileState
import com.alirsyad.app.data.state.UpdateState
import com.alirsyad.app.data.state.UploadPhotoState
import com.alirsyad.app.utils.toMultiPartBody
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import java.io.File
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val banner: MutableLiveData<BannerState> by lazy {
        MutableLiveData()
    }

    val newUpdate: MutableLiveData<UpdateState> by lazy {
        MutableLiveData()
    }

    val subjectClass: MutableLiveData<TingkatState> by lazy {
        MutableLiveData()
    }

    val subjectCourse: MutableLiveData<CourseState> by lazy {
        MutableLiveData()
    }

    val achievement: MutableLiveData<AchievementSimulationState> by lazy {
        MutableLiveData()
    }

    val profile: MutableLiveData<ProfileState> by lazy {
        MutableLiveData()
    }

    val updateProfile: MutableLiveData<UpdateProfileState> by lazy {
        MutableLiveData()
    }

    val uploadPhoto: MutableLiveData<UploadPhotoState> by lazy {
        MutableLiveData()
    }

    val yourAchievement: MutableLiveData<EReportAchievementState> by lazy {
        MutableLiveData()
    }

    val eReportChart: MutableLiveData<EReportChartState> by lazy {
        MutableLiveData()
    }

    val eReportCurrentScore: MutableLiveData<EReportCurrentScoreState> by lazy {
        MutableLiveData()
    }

    fun getPhoto(): String {
        return repository.getPhoto()
    }

    fun getName(): String {
        return repository.getName()
    }

    fun getKelas(): String {
        return repository.getKelas()
    }

    fun getTingkat(): String {
        return repository.getSubjectClass()
    }

    fun getTingkatId(): Int {
        return repository.getTingkatId()
    }

    fun getIsPengunjung(): Int {
        return repository.getIsPengunjung()
    }

    fun getStatus(): String {
        return repository.getStatus()
    }

    fun getBanner() {
        repository.getHomeBanner(repository.getToken(), banner)
    }

    fun getNewUpdate() {
        repository.getUpdate(repository.getToken(), newUpdate)
    }

    fun getClass() {
        repository.getSubjectClass(repository.getToken(), subjectClass)
    }

    fun getMapelByClass(tingkat: Int) {
        repository.getMapelByClass(repository.getToken(), tingkat, subjectCourse)
    }

    fun setClassPosition(position: Int) {
        repository.setPosition(position)
    }

    fun getClassPosition(): Int {
        return repository.getPosition()
    }

    fun setClassId(id: Int) {
        repository.setClassId(id)
    }

    fun getClassId(): Int {
        return repository.getClassId()
    }

    fun getSubjectClass() {
        repository.getSubjectClass(repository.getToken(), subjectClass)
    }

    fun getSubjectCourse(tingkat: Int) {
        repository.getMapelByClass(repository.getToken(), tingkat, subjectCourse)
    }

    fun getSubjectCourse() {
        repository.getMapelByClass(repository.getToken(), repository.getTingkatId(), subjectCourse)
    }

    fun getAchievement(courseId: Int) {
        repository.getAchievementSimulation(repository.getToken(), courseId, achievement)
    }

    fun getYourAchievement() {
        repository.getEReportAchievement(
            repository.getToken(),
            repository.getTingkatId(),
            getUserId(),
            yourAchievement
        )
    }

    fun getEReportChart() {
        repository.getEReportChart(
            repository.getToken(),
            repository.getTingkatId(),
            getUserId(),
            eReportChart
        )
    }

    fun getEReportCurrentScore() {
        repository.getEReportCurrentScore(
            repository.getToken(),
            getUserId(),
            eReportCurrentScore
        )
    }

    fun getProfile(){
        repository.getProfile(repository.getToken(), profile)
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

    fun uploadPhoto(
        file: File
    ){
        repository.uploadPhoto(
            repository.getToken(),
            file.toMultiPartBody("file"),
            uploadPhoto,
        )
    }

    fun updateSessionPhoto(photo: String){
        repository.updatePhoto(photo)
    }

    fun getUserId() = repository.getUserId()

    fun logOut(){
        repository.setIsLogin(false)
    }

    fun setIsTab(isTab: Boolean) {
        repository.setIsTabClass(isTab)
    }

    fun getIsTab() = repository.getIsTabClass()
}