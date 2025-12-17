package com.alirsyad.app.data.repository.local

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.alirsyad.app.data.database.RoomDB
import com.alirsyad.app.data.entity.QuestionEntity
import com.alirsyad.app.data.model.previewadaptive.PreviewAdaptiveResponse
import com.alirsyad.app.data.model.user.DataUser
import com.alirsyad.app.data.session.Sessions
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
import com.alirsyad.app.utils.Constant.Bearer
import com.alirsyad.app.utils.loadJSONFromAssets
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(
    private val db: RoomDB,
    private val sessions: Sessions,
    private val disposable: CompositeDisposable,
    private val context: Context
) : com.alirsyad.app.data.repository.Repository {
    override fun login(
        body: JsonObject,
        callback: MutableLiveData<LoginState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getHomeBanner(
        token: String,
        callback: MutableLiveData<BannerState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getUpdate(
        token: String,
        callback: MutableLiveData<UpdateState>) {
        throw UnsupportedOperationException()
    }

    override fun getSubjectClass(
        token: String,
        callback: MutableLiveData<TingkatState>) {
        throw UnsupportedOperationException()
    }

    override fun getCourse(
        token: String,
        callback: MutableLiveData<CourseState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getCourseDetail(
        token: String,
        id: Int,
        callback: MutableLiveData<DetailCourseState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getMapelByClass(
        token: String,
        id: Int,
        callback: MutableLiveData<CourseState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getCourseInProgress(
        token: String,
        callback: MutableLiveData<CourseState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getCourseUpComing(
        token: String,
        callback: MutableLiveData<CourseState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getCourseDone(token: String, callback: MutableLiveData<CourseState>) {
        throw UnsupportedOperationException()
    }

    override fun getCourseSummary(
        token: String,
        id: Int,
        callback: MutableLiveData<CourseSummaryState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getCourseModule(
        token: String,
        id: Int,
        callback: MutableLiveData<CourseModuleState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getCourseVideo(
        token: String,
        id: Int,
        callback: MutableLiveData<CourseVideoState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getCourseSimulation(
        token: String,
        id: Int,
        callback: MutableLiveData<CourseSimulationState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getSimulation(
        token: String,
        callback: MutableLiveData<SimulationState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getDetailModule(
        token: String,
        id: Int,
        callback: MutableLiveData<DetailModuleState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getDetailVideo(
        token: String,
        id: Int,
        callback: MutableLiveData<DetailVideoState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getDetailSimulation(
        token: String,
        id: Int,
        callback: MutableLiveData<DetailSimulationState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun saveFlagModule(
        token: String,
        id: Int,
        callback: MutableLiveData<SaveModuleState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun saveFlagVideo(
        token: String, id: Int,
        callback: MutableLiveData<SaveVideoState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getProfile(token: String, callback: MutableLiveData<ProfileState>) {
        throw UnsupportedOperationException()
    }

    override fun updateProfile(
        token: String,
        request: JsonObject,
        callback: MutableLiveData<UpdateProfileState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun updatePassword(
        token: String,
        request: JsonObject,
        callback: MutableLiveData<UpdateProfileState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun uploadPhoto(
        token: String,
        file: MultipartBody.Part,
        callback: MutableLiveData<UploadPhotoState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getListJenjang(callback: MutableLiveData<JenjangState>) {
        throw UnsupportedOperationException()
    }

    override fun register(request: JsonObject, callback: MutableLiveData<RegisterState>) {
        throw UnsupportedOperationException()
    }

    override fun getCourseActive(token: String, callback: MutableLiveData<CourseState>) {
        throw UnsupportedOperationException()
    }

    override fun getCourseNonActive(token: String, callback: MutableLiveData<CourseState>) {
        throw UnsupportedOperationException()
    }

    override fun forgotPassword(
        request: JsonObject,
        callback: MutableLiveData<CommonState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun forgotPasswordStudent(
        request: JsonObject,
        callback: MutableLiveData<CommonState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun resetPassword(request: JsonObject, callback: MutableLiveData<CommonState>) {
        throw UnsupportedOperationException()
    }

    override fun getQuestionPackage(
        token: String,
        mataPelajaranId: Int,
        callback: MutableLiveData<ResponseArrayState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getQuestion(token: String, paketSoalId: Int, callback: MutableLiveData<QuestionState>) {
        throw UnsupportedOperationException()
    }

    override fun getQuestion(callback: MutableLiveData<List<QuestionEntity>>) {
        db.questionDao().getQuestion()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toFlowable()
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getLevel(
        token: String,
        paketSoalId: Int,
        siswaId: Int,
        callback: MutableLiveData<LevelState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getPreviewAdaptive(
        token: String,
        request: JsonObject,
        callback: MutableLiveData<PreviewAdaptiveState>
    ) {
        val jsonObject = context.loadJSONFromAssets("preview_adaptive.json")
        val data = Gson().fromJson(jsonObject, PreviewAdaptiveResponse::class.java)
        val result = Single.just(data)
        result.map<PreviewAdaptiveState>(PreviewAdaptiveState::Result)
            .onErrorReturn(PreviewAdaptiveState::Error)
            .toFlowable()
            .startWith(PreviewAdaptiveState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun checkAnswer(token: String, request: JsonObject, callback: MutableLiveData<CheckAnswerState>) {
        throw UnsupportedOperationException()
    }

    override fun getAchievementSimulation(
        token: String,
        courseId: Int,
        callback: MutableLiveData<AchievementSimulationState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getEReportYourScore(
        token: String,
        userId: Int,
        courseId: Int,
        callback: MutableLiveData<EReportScoreState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getEReportChart(
        token: String,
        levelId: Int,
        userId: Int,
        callback: MutableLiveData<EReportChartState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getSummaryOfLevel(
        token: String,
        courseId: Int,
        userId: Int,
        callback: MutableLiveData<SummaryOfLevelState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getEReportAchievement(
        token: String,
        levelId: Int,
        userId: Int,
        callback: MutableLiveData<EReportAchievementState>
    ) {
        throw UnsupportedOperationException()
    }

    override fun getEReportCurrentScore(
        token: String,
        userId: Int,
        callback: MutableLiveData<EReportCurrentScoreState>
    ) {
        throw UnsupportedOperationException()
    }

    override suspend fun insertQuestion(data: List<QuestionEntity>, callback: MutableLiveData<Int>) {
        db.questionDao().insertQuestion(data)
    }

    override suspend fun updateQuestion(data: QuestionEntity, callback: MutableLiveData<Int>) {
        db.questionDao().updateQuestion(data)
    }

    override suspend fun clearQuestion(callback: MutableLiveData<Int>) {
        db.questionDao().deleteAllQuestion()
    }

    override fun getIsPengunjung(): Int {
        return sessions.getInt(Sessions.isPengunjung)
    }

    override fun updatePhoto(photo: String) {
        sessions.putString(Sessions.photo, photo)
    }

    override fun updateEmail(email: String) {
        sessions.putString(Sessions.email, email)
    }

    override fun setIsLogin(isLogin: Boolean) {
        sessions.putBoolean(Sessions.isLogin, isLogin)
    }

    override fun setPosition(position: Int) {
        sessions.putInt(Sessions.classPosition, position)
    }

    override fun setClassId(id: Int) {
        sessions.putInt(Sessions.classId, id)
    }

    override fun getPosition(): Int {
        return sessions.getInt(Sessions.classPosition)
    }

    override fun getClassId(): Int {
        return sessions.getInt(Sessions.classId)
    }

    override fun getTingkatId(): Int {
        return sessions.getInt(Sessions.tingkatId)
    }

    override fun setIsTabClass(isTab: Boolean) {
        sessions.putBoolean(Sessions.isTab, isTab)
    }

    override fun getIsTabClass(): Boolean {
        return sessions.getBoolean(Sessions.isTab)
    }

    override fun getUserId(): Int {
        return sessions.getInt(Sessions.userId)
    }

    override fun getToken(): String {
        return sessions.getString(Sessions.token)
    }

    override fun getNis(): String {
        return sessions.getString(Sessions.nis)
    }

    override fun getName(): String {
        return sessions.getString(Sessions.name)
    }

    override fun getKelas(): String {
        return sessions.getString(Sessions.kelas)
    }

    override fun getSubjectClass(): String {
        return sessions.getString(Sessions.tingkat)
    }

    override fun getJenjang(): String {
        return sessions.getString(Sessions.jenjang)
    }

    override fun getEmail(): String {
        return sessions.getString(Sessions.email)
    }

    override fun getPhoto(): String {
        return sessions.getString(Sessions.photo)
    }

    override fun getStatus(): String {
        return sessions.getString(Sessions.status)
    }

    override fun setDataUser(data: DataUser) {
        sessions.batch().edit {
            putString(Sessions.token, Bearer + data.token)
            putString(Sessions.name, data.name)
            putString(Sessions.nis, data.nis)
            putString(Sessions.kelas, data.kelas)
            putString(Sessions.tingkat, data.tingkat)
            putInt(Sessions.tingkatId, data.tingkatId)
            putString(Sessions.jenjang, data.jenjang)
            putString(Sessions.email, data.email)
            putString(Sessions.photo, data.photo)
            putString(Sessions.role, data.role)
            putInt(Sessions.isPengunjung, data.isPengunjung)
            putString(Sessions.status, data.status)
            putBoolean(Sessions.isLogin, true)
            putInt(Sessions.userId, data.userId)
        }
    }

    override fun setIntro(isIntro: Boolean) {
        sessions.putBoolean(Sessions.isIntro, isIntro)
    }

    override fun getIsLogin(): Boolean {
        return sessions.getBoolean(Sessions.isLogin)
    }

    override fun getIsIntro(): Boolean {
        return sessions.getBoolean(Sessions.isIntro)
    }

    override fun logOut() {
        sessions.logOut()
    }


    override fun getDisposable(): CompositeDisposable {
        throw UnsupportedOperationException()
    }

    override fun getDatabase(): RoomDB {
        return db
    }

}