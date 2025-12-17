package com.alirsyad.app.data.repository

import androidx.lifecycle.MutableLiveData
import com.alirsyad.app.data.database.RoomDB
import com.alirsyad.app.data.entity.QuestionEntity
import com.alirsyad.app.data.repository.local.LocalRepository
import com.alirsyad.app.data.repository.remote.RemoteRepository
import com.alirsyad.app.data.state.AchievementSimulationState
import com.alirsyad.app.data.state.BannerState
import com.alirsyad.app.data.state.CheckAnswerState
import com.alirsyad.app.data.state.CommonState
import com.alirsyad.app.data.state.CourseModuleState
import com.alirsyad.app.data.state.CourseSimulationState
import com.alirsyad.app.data.state.CourseState
import com.alirsyad.app.data.state.CourseSummaryState
import com.alirsyad.app.data.state.CourseVideoState
import com.alirsyad.app.data.state.DetailCourseState
import com.alirsyad.app.data.state.DetailModuleState
import com.alirsyad.app.data.state.DetailSimulationState
import com.alirsyad.app.data.state.DetailVideoState
import com.alirsyad.app.data.state.EReportAchievementState
import com.alirsyad.app.data.state.EReportChartState
import com.alirsyad.app.data.state.EReportCurrentScoreState
import com.alirsyad.app.data.state.EReportScoreState
import com.alirsyad.app.data.state.JenjangState
import com.alirsyad.app.data.state.LevelState
import com.alirsyad.app.data.state.LoginState
import com.alirsyad.app.data.state.PreviewAdaptiveState
import com.alirsyad.app.data.state.ProfileState
import com.alirsyad.app.data.state.QuestionState
import com.alirsyad.app.data.state.RegisterState
import com.alirsyad.app.data.state.ResponseArrayState
import com.alirsyad.app.data.state.SaveModuleState
import com.alirsyad.app.data.state.SaveVideoState
import com.alirsyad.app.data.state.SimulationState
import com.alirsyad.app.data.state.SummaryOfLevelState
import com.alirsyad.app.data.state.TingkatState
import com.alirsyad.app.data.state.UpdateProfileState
import com.alirsyad.app.data.state.UpdateState
import com.alirsyad.app.data.state.UploadPhotoState
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MultipartBody
import javax.inject.Inject

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

class DataRepository @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : Repository {
    override fun login(
        body: JsonObject,
        callback: MutableLiveData<LoginState>
    ) {
        remoteRepository.login(body, callback)
    }

    override fun getHomeBanner(
        token: String,
        callback: MutableLiveData<BannerState>
    ) {
        remoteRepository.getHomeBanner(token, callback)
    }

    override fun getUpdate(
        token: String,
        callback: MutableLiveData<UpdateState>) {
        remoteRepository.getUpdate(token, callback)
    }

    override fun getSubjectClass(
        token: String,
        callback: MutableLiveData<TingkatState>
    ) {
        remoteRepository.getSubjectClass(token, callback)
    }

    override fun getCourse(
        token: String,
        callback: MutableLiveData<CourseState>
    ) {
        remoteRepository.getCourse(token, callback)
    }

    override fun getCourseInProgress(
        token: String,
        callback: MutableLiveData<CourseState>
    ) {
        remoteRepository.getCourseInProgress(token, callback)
    }

    override fun getCourseUpComing(
        token: String,
        callback: MutableLiveData<CourseState>
    ) {
        remoteRepository.getCourseUpComing(token, callback)
    }

    override fun getCourseDone(token: String, callback: MutableLiveData<CourseState>) {
        remoteRepository.getCourseDone(token, callback)
    }

    override fun getCourseSummary(
        token: String,
        id: Int,
        callback: MutableLiveData<CourseSummaryState>
    ) {
        remoteRepository.getCourseSummary(token, id, callback)
    }

    override fun getCourseModule(
        token: String,
        id: Int,
        callback: MutableLiveData<CourseModuleState>
    ) {
        remoteRepository.getCourseModule(token, id, callback)
    }

    override fun getCourseDetail(
        token: String,
        id: Int,
        callback: MutableLiveData<DetailCourseState>
    ) {
        remoteRepository.getCourseDetail(token, id, callback)
    }

    override fun getMapelByClass(
        token: String,
        id: Int,
        callback: MutableLiveData<CourseState>
    ) {
        remoteRepository.getMapelByClass(token, id, callback)
    }

    override fun getCourseVideo(
        token: String,
        id: Int,
        callback: MutableLiveData<CourseVideoState>
    ) {
        remoteRepository.getCourseVideo(token, id, callback)
    }

    override fun getCourseSimulation(
        token: String,
        id: Int,
        callback: MutableLiveData<CourseSimulationState>
    ) {
        remoteRepository.getCourseSimulation(token, id, callback)
    }

    override fun getSimulation(
        token: String,
        callback: MutableLiveData<SimulationState>
    ) {
        remoteRepository.getSimulation(token, callback)
    }

    override fun getDetailModule(
        token: String,
        id: Int,
        callback: MutableLiveData<DetailModuleState>
    ) {
        remoteRepository.getDetailModule(token, id, callback)
    }

    override fun getDetailVideo(
        token: String,
        id: Int,
        callback: MutableLiveData<DetailVideoState>
    ) {
        remoteRepository.getDetailVideo(token, id, callback)
    }

    override fun getDetailSimulation(
        token: String,
        id: Int,
        callback: MutableLiveData<DetailSimulationState>
    ) {
        remoteRepository.getDetailSimulation(token, id, callback)
    }

    override fun saveFlagModule(
        token: String,
        id: Int,
        callback: MutableLiveData<SaveModuleState>
    ) {
        remoteRepository.saveFlagModule(token, id, callback)
    }

    override fun saveFlagVideo(
        token: String, id: Int,
        callback: MutableLiveData<SaveVideoState>
    ) {
        remoteRepository.saveFlagVideo(token, id, callback)
    }

    override fun getProfile(token: String, callback: MutableLiveData<ProfileState>) {
        remoteRepository.getProfile(token,callback)
    }

    override fun updateProfile(
        token: String,
        request: JsonObject,
        callback: MutableLiveData<UpdateProfileState>
    ) {
        remoteRepository.updateProfile(token,request,callback)
    }

    override fun updatePassword(
        token: String,
        request: JsonObject,
        callback: MutableLiveData<UpdateProfileState>
    ) {
        remoteRepository.updatePassword(token,request, callback)
    }

    override fun uploadPhoto(
        token: String,
        file: MultipartBody.Part,
        callback: MutableLiveData<UploadPhotoState>
    ) {
        remoteRepository.uploadPhoto(token, file, callback)
    }

    override fun getListJenjang(callback: MutableLiveData<JenjangState>) {
        remoteRepository.getListJenjang(callback)
    }

    override fun register(request: JsonObject, callback: MutableLiveData<RegisterState>) {
        remoteRepository.register(request, callback)
    }

    override fun getCourseActive(token: String, callback: MutableLiveData<CourseState>) {
        remoteRepository.getCourseActive(token, callback)
    }

    override fun getCourseNonActive(token: String, callback: MutableLiveData<CourseState>) {
        remoteRepository.getCourseNonActive(token, callback)
    }

    override fun forgotPassword(
        request: JsonObject,
        callback: MutableLiveData<CommonState>
    ) {
        remoteRepository.forgotPassword(request, callback)
    }

    override fun forgotPasswordStudent(
        request: JsonObject,
        callback: MutableLiveData<CommonState>
    ) {
        remoteRepository.forgotPasswordStudent(request, callback)
    }

    override fun resetPassword(request: JsonObject, callback: MutableLiveData<CommonState>) {
        remoteRepository.resetPassword(request, callback)
    }

    override fun getQuestionPackage(
        token: String,
        mataPelajaranId: Int,
        callback: MutableLiveData<ResponseArrayState>
    ) {
        remoteRepository.getQuestionPackage(token, mataPelajaranId, callback)
    }

    override fun getQuestion(token: String, paketSoalId: Int, callback: MutableLiveData<QuestionState>) {
        remoteRepository.getQuestion(token, paketSoalId, callback)
    }

    override fun getQuestion(callback: MutableLiveData<List<QuestionEntity>>) {
        localRepository.getQuestion(callback)
    }

    override fun getLevel(
        token: String,
        paketSoalId: Int,
        siswaId: Int,
        callback: MutableLiveData<LevelState>
    ) {
        remoteRepository.getLevel(token, paketSoalId, siswaId, callback)
    }

    override fun checkAnswer(token: String, request: JsonObject, callback: MutableLiveData<CheckAnswerState>) {
        remoteRepository.checkAnswer(token, request, callback)
    }

    override fun getPreviewAdaptive(
        token: String,
        request: JsonObject,
        callback: MutableLiveData<PreviewAdaptiveState>
    ) {
        remoteRepository.getPreviewAdaptive(token, request, callback)
    }

    override fun getAchievementSimulation(
        token: String,
        courseId: Int,
        callback: MutableLiveData<AchievementSimulationState>
    ) {
        remoteRepository.getAchievementSimulation(token, courseId, callback)
    }

    override fun getEReportYourScore(
        token: String,
        userId: Int,
        courseId: Int,
        callback: MutableLiveData<EReportScoreState>
    ) {
        remoteRepository.getEReportYourScore(token, userId, courseId, callback)
    }

    override fun getEReportChart(
        token: String,
        levelId: Int,
        userId: Int,
        callback: MutableLiveData<EReportChartState>
    ) {
        remoteRepository.getEReportChart(token, levelId, userId, callback)
    }

    override fun getSummaryOfLevel(
        token: String,
        courseId: Int,
        userId: Int,
        callback: MutableLiveData<SummaryOfLevelState>
    ) {
        remoteRepository.getSummaryOfLevel(token, courseId, userId, callback)
    }

    override fun getEReportAchievement(
        token: String,
        levelId: Int,
        userId: Int,
        callback: MutableLiveData<EReportAchievementState>
    ) {
        remoteRepository.getEReportAchievement(token, levelId, userId, callback)
    }

    override fun getEReportCurrentScore(
        token: String,
        userId: Int,
        callback: MutableLiveData<EReportCurrentScoreState>
    ) {
        remoteRepository.getEReportCurrentScore(token, userId, callback)
    }

    override suspend fun insertQuestion(data: List<QuestionEntity>, callback: MutableLiveData<Int>) {
        localRepository.insertQuestion(data, callback)
    }

    override suspend fun updateQuestion(data: QuestionEntity, callback: MutableLiveData<Int>) {
        localRepository.updateQuestion(data, callback)
    }

    override suspend fun clearQuestion(callback: MutableLiveData<Int>) {
        localRepository.clearQuestion(callback)
    }

    override fun getIsPengunjung(): Int {
        return localRepository.getIsPengunjung()
    }

    override fun updatePhoto(photo: String) {
        localRepository.updatePhoto(photo)
    }

    override fun updateEmail(email: String) {
        localRepository.updateEmail(email)
    }

    override fun setIsLogin(isLogin: Boolean) {
        localRepository.setIsLogin(isLogin)
    }

    override fun setPosition(position: Int) {
        localRepository.setPosition(position)
    }

    override fun setClassId(id: Int) {
        localRepository.setClassId(id)
    }

    override fun getPosition(): Int {
        return localRepository.getPosition()
    }

    override fun getClassId(): Int {
        return localRepository.getClassId()
    }

    override fun getTingkatId(): Int {
        return localRepository.getTingkatId()
    }

    override fun setIsTabClass(isTab: Boolean) {
        localRepository.setIsTabClass(isTab)
    }

    override fun getIsTabClass(): Boolean {
       return localRepository.getIsTabClass()
    }

    override fun getUserId(): Int {
        return localRepository.getUserId()
    }

    override fun getToken(): String {
        return localRepository.getToken()
    }

    override fun getNis(): String {
        return localRepository.getNis()
    }

    override fun getName(): String {
        return localRepository.getName()
    }

    override fun getKelas(): String {
        return localRepository.getKelas()
    }

    override fun getSubjectClass(): String {
        return localRepository.getSubjectClass()
    }

    override fun getJenjang(): String {
        return localRepository.getJenjang()
    }

    override fun getEmail(): String {
        return localRepository.getEmail()
    }

    override fun getPhoto(): String {
        return localRepository.getPhoto()
    }

    override fun getStatus(): String {
        return localRepository.getStatus()
    }

    override fun setDataUser(data: com.alirsyad.app.data.model.user.DataUser) {
        localRepository.setDataUser(data)
    }

    override fun setIntro(isIntro: Boolean) {
        localRepository.setIntro(isIntro)
    }

    override fun getIsLogin(): Boolean {
        return localRepository.getIsLogin()
    }

    override fun getIsIntro(): Boolean {
        return localRepository.getIsIntro()
    }

    override fun logOut() {
        localRepository.logOut()
    }

    override fun getDisposable(): CompositeDisposable {
        return remoteRepository.getDisposable()
    }

    override fun getDatabase(): RoomDB {
        return localRepository.getDatabase()
    }

}