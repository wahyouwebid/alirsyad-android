package com.alirsyad.app.data.repository.remote

import androidx.lifecycle.MutableLiveData
import com.alirsyad.app.data.database.RoomDB
import com.alirsyad.app.data.entity.QuestionEntity
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

class RemoteRepository @Inject constructor(
    private val disposable: CompositeDisposable,
    private val apiService: com.alirsyad.app.data.network.ApiService
) : com.alirsyad.app.data.repository.Repository {

    override fun login(body: JsonObject, callback: MutableLiveData<LoginState>) {
        apiService.getHeadline(body)
            .map<LoginState>(LoginState::Result)
            .onErrorReturn(LoginState::Error)
            .toFlowable()
            .startWith(LoginState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getHomeBanner(token: String, callback: MutableLiveData<BannerState>) {
        apiService.getHomeBanner(token)
            .map<BannerState>(BannerState::Result)
            .onErrorReturn(BannerState::Error)
            .toFlowable()
            .startWith(BannerState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getUpdate(token: String, callback: MutableLiveData<UpdateState>) {
        apiService.getUpdate(token)
            .map<UpdateState>(UpdateState::Result)
            .onErrorReturn(UpdateState::Error)
            .toFlowable()
            .startWith(UpdateState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getSubjectClass(token: String, callback: MutableLiveData<TingkatState>) {
        apiService.getTingkat(token)
            .map<TingkatState>(TingkatState::Result)
            .onErrorReturn(TingkatState::Error)
            .toFlowable()
            .startWith(TingkatState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getCourse(token: String, callback: MutableLiveData<CourseState>) {
        apiService.getCourses(token)
            .map<CourseState>(CourseState::Result)
            .onErrorReturn(CourseState::Error)
            .toFlowable()
            .startWith(CourseState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getCourseDetail(token: String, id: Int, callback: MutableLiveData<DetailCourseState>) {
        apiService.getCourseDetail(token, id)
            .map<DetailCourseState>(DetailCourseState::Result)
            .onErrorReturn(DetailCourseState::Error)
            .toFlowable()
            .startWith(DetailCourseState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getCourseInProgress(token: String, callback: MutableLiveData<CourseState>) {
        apiService.getCoursesInProgress(token)
            .map<CourseState>(CourseState::Result)
            .onErrorReturn(CourseState::Error)
            .toFlowable()
            .startWith(CourseState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getCourseUpComing(token: String, callback: MutableLiveData<CourseState>) {
        apiService.getCoursesUpComing(token)
            .map<CourseState>(CourseState::Result)
            .onErrorReturn(CourseState::Error)
            .toFlowable()
            .startWith(CourseState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getCourseDone(token: String, callback: MutableLiveData<CourseState>) {
        apiService.getCoursesDone(token)
            .map<CourseState>(CourseState::Result)
            .onErrorReturn(CourseState::Error)
            .toFlowable()
            .startWith(CourseState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getMapelByClass(token: String, id: Int, callback: MutableLiveData<CourseState>) {
        apiService.getCourseByTingkat(token, id)
            .map<CourseState>(CourseState::Result)
            .onErrorReturn(CourseState::Error)
            .toFlowable()
            .startWith(CourseState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getCourseSummary(
        token: String,
        id: Int,
        callback: MutableLiveData<CourseSummaryState>
    ) {
        apiService.getCourseSummary(token, id)
            .map<CourseSummaryState>(CourseSummaryState::Result)
            .onErrorReturn(CourseSummaryState::Error)
            .toFlowable()
            .startWith(CourseSummaryState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getCourseModule(
        token: String,
        id: Int,
        callback: MutableLiveData<CourseModuleState>
    ) {
        apiService.getCourseModule(token, id)
            .map<CourseModuleState>(CourseModuleState::Result)
            .onErrorReturn(CourseModuleState::Error)
            .toFlowable()
            .startWith(CourseModuleState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getCourseVideo(
        token: String,
        id: Int,
        callback: MutableLiveData<CourseVideoState>
    ) {
        apiService.getCourseVideos(token, id)
            .map<CourseVideoState>(CourseVideoState::Result)
            .onErrorReturn(CourseVideoState::Error)
            .toFlowable()
            .startWith(CourseVideoState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getCourseSimulation(
        token: String,
        id: Int,
        callback: MutableLiveData<CourseSimulationState>
    ) {
        apiService.getCourseSimulation(token, id)
            .map<CourseSimulationState>(CourseSimulationState::Result)
            .onErrorReturn(CourseSimulationState::Error)
            .toFlowable()
            .startWith(CourseSimulationState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getSimulation(
        token: String,
        callback: MutableLiveData<SimulationState>
    ) {
        apiService.getSimulation(token)
            .map<SimulationState>(SimulationState::Result)
            .onErrorReturn(SimulationState::Error)
            .toFlowable()
            .startWith(SimulationState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getDetailModule(
        token: String,
        id: Int,
        callback: MutableLiveData<DetailModuleState>
    ) {
        apiService.getDetailModule(token, id)
            .map<DetailModuleState>(DetailModuleState::Result)
            .onErrorReturn(DetailModuleState::Error)
            .toFlowable()
            .startWith(DetailModuleState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getDetailVideo(
        token: String,
        id: Int,
        callback: MutableLiveData<DetailVideoState>
    ) {
        apiService.getDetailVideos(token, id)
            .map<DetailVideoState>(DetailVideoState::Result)
            .onErrorReturn(DetailVideoState::Error)
            .toFlowable()
            .startWith(DetailVideoState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getDetailSimulation(
        token: String,
        id: Int,
        callback: MutableLiveData<DetailSimulationState>
    ) {
        apiService.getDetailSimulation(token, id)
            .map<DetailSimulationState>(DetailSimulationState::Result)
            .onErrorReturn(DetailSimulationState::Error)
            .toFlowable()
            .startWith(DetailSimulationState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun saveFlagModule(
        token: String,
        id: Int,
        callback: MutableLiveData<SaveModuleState>
    ) {
        apiService.saveFlagModule(token, id)
            .map<SaveModuleState>(SaveModuleState::Result)
            .onErrorReturn(SaveModuleState::Error)
            .toFlowable()
            .startWith(SaveModuleState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun saveFlagVideo(
        token: String, id: Int,
        callback: MutableLiveData<SaveVideoState>
    ) {
        apiService.saveFlagVideo(token, id)
            .map<SaveVideoState>(SaveVideoState::Result)
            .onErrorReturn(SaveVideoState::Error)
            .toFlowable()
            .startWith(SaveVideoState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getProfile(token: String, callback: MutableLiveData<ProfileState>) {
        apiService.getProfile(token)
            .map<ProfileState>(ProfileState::Result)
            .onErrorReturn(ProfileState::Error)
            .toFlowable()
            .startWith(ProfileState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun updateProfile(
        token: String,
        request: JsonObject,
        callback: MutableLiveData<UpdateProfileState>
    ) {
        apiService.updateProfile(token,request)
            .map<UpdateProfileState>(UpdateProfileState::Result)
            .onErrorReturn(UpdateProfileState::Error)
            .toFlowable()
            .startWith(UpdateProfileState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun updatePassword(
        token: String,
        request: JsonObject,
        callback: MutableLiveData<UpdateProfileState>
    ) {
        apiService.updatePassword(token,request)
            .map<UpdateProfileState>(UpdateProfileState::Result)
            .onErrorReturn(UpdateProfileState::Error)
            .toFlowable()
            .startWith(UpdateProfileState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun uploadPhoto(
        token: String,
        file: MultipartBody.Part,
        callback: MutableLiveData<UploadPhotoState>
    ) {
        apiService.uploadPhoto(token,file)
            .map<UploadPhotoState>(UploadPhotoState::Result)
            .onErrorReturn(UploadPhotoState::Error)
            .toFlowable()
            .startWith(UploadPhotoState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getListJenjang(callback: MutableLiveData<JenjangState>) {
        apiService.getJenjang()
            .map<JenjangState>(JenjangState::Result)
            .onErrorReturn(JenjangState::Error)
            .toFlowable()
            .startWith(JenjangState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun register(request: JsonObject, callback: MutableLiveData<RegisterState>) {
        apiService.register(request)
            .map<RegisterState>(RegisterState::Result)
            .onErrorReturn(RegisterState::Error)
            .toFlowable()
            .startWith(RegisterState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getCourseActive(token: String, callback: MutableLiveData<CourseState>) {
        apiService.getCoursesActive(token)
            .map<CourseState>(CourseState::Result)
            .onErrorReturn(CourseState::Error)
            .toFlowable()
            .startWith(CourseState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getCourseNonActive(token: String, callback: MutableLiveData<CourseState>) {
        apiService.getCoursesNonActive(token)
            .map<CourseState>(CourseState::Result)
            .onErrorReturn(CourseState::Error)
            .toFlowable()
            .startWith(CourseState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun forgotPassword(
        request: JsonObject,
        callback: MutableLiveData<CommonState>
    ) {
        apiService.forgotPassword(request)
            .map<CommonState>(CommonState::Result)
            .onErrorReturn(CommonState::Error)
            .toFlowable()
            .startWith(CommonState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun forgotPasswordStudent(
        request: JsonObject,
        callback: MutableLiveData<CommonState>
    ) {
        apiService.forgotPasswordStudent(request)
            .map<CommonState>(CommonState::Result)
            .onErrorReturn(CommonState::Error)
            .toFlowable()
            .startWith(CommonState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun resetPassword(request: JsonObject, callback: MutableLiveData<CommonState>) {
        apiService.resetPassword(request)
            .map<CommonState>(CommonState::Result)
            .onErrorReturn(CommonState::Error)
            .toFlowable()
            .startWith(CommonState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getQuestionPackage(
        token: String,
        mataPelajaranId: Int,
        callback: MutableLiveData<ResponseArrayState>
    ) {
        apiService.getQuestionPackage(token, mataPelajaranId)
            .map<ResponseArrayState>(ResponseArrayState::Result)
            .onErrorReturn(ResponseArrayState::Error)
            .toFlowable()
            .startWith(ResponseArrayState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getQuestion(token: String, paketSoalId: Int, callback: MutableLiveData<QuestionState>) {
        apiService.getQuestion(token, paketSoalId)
            .map<QuestionState>(QuestionState::Result)
            .onErrorReturn(QuestionState::Error)
            .toFlowable()
            .startWith(QuestionState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getQuestion(callback: MutableLiveData<List<QuestionEntity>>) {
        throw UnsupportedOperationException()
    }

    override fun getLevel(
        token: String,
        paketSoalId: Int,
        siswaId: Int,
        callback: MutableLiveData<LevelState>
    ) {
        apiService.getLevel(token, paketSoalId, siswaId)
            .map<LevelState>(LevelState::Result)
            .onErrorReturn(LevelState::Error)
            .toFlowable()
            .startWith(LevelState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun checkAnswer(token: String,request: JsonObject, callback: MutableLiveData<CheckAnswerState>) {
        apiService.checkAnswer(token, request)
            .map<CheckAnswerState>(CheckAnswerState::Result)
            .onErrorReturn(CheckAnswerState::Error)
            .toFlowable()
            .startWith(CheckAnswerState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getPreviewAdaptive(
        token: String,
        request: JsonObject,
        callback: MutableLiveData<PreviewAdaptiveState>
    ) {
        apiService.getPreviewQuestion(token, request)
            .map<PreviewAdaptiveState>(PreviewAdaptiveState::Result)
            .onErrorReturn(PreviewAdaptiveState::Error)
            .toFlowable()
            .startWith(PreviewAdaptiveState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getAchievementSimulation(
        token: String,
        courseId: Int,
        callback: MutableLiveData<AchievementSimulationState>
    ) {
        apiService.getAchievementSimulation(token, courseId)
            .map<AchievementSimulationState>(AchievementSimulationState::Result)
            .onErrorReturn(AchievementSimulationState::Error)
            .toFlowable()
            .startWith(AchievementSimulationState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getEReportYourScore(
        token: String,
        userId: Int,
        courseId: Int,
        callback: MutableLiveData<EReportScoreState>
    ) {
        apiService.getEReportYourScore(token, courseId, userId)
            .map<EReportScoreState>(EReportScoreState::Result)
            .onErrorReturn(EReportScoreState::Error)
            .toFlowable()
            .startWith(EReportScoreState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getEReportChart(
        token: String,
        levelId: Int,
        userId: Int,
        callback: MutableLiveData<EReportChartState>
    ) {
        apiService.getEReportChart(token, levelId, userId)
            .map<EReportChartState>(EReportChartState::Result)
            .onErrorReturn(EReportChartState::Error)
            .toFlowable()
            .startWith(EReportChartState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getSummaryOfLevel(
        token: String,
        courseId: Int,
        userId: Int,
        callback: MutableLiveData<SummaryOfLevelState>
    ) {
        apiService.getSummaryOfLevel(token, courseId, userId)
            .map<SummaryOfLevelState>(SummaryOfLevelState::Result)
            .onErrorReturn(SummaryOfLevelState::Error)
            .toFlowable()
            .startWith(SummaryOfLevelState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getEReportAchievement(
        token: String,
        levelId: Int,
        userId: Int,
        callback: MutableLiveData<EReportAchievementState>
    ) {
        apiService.getHomeAchievement(token, levelId, userId)
            .map<EReportAchievementState>(EReportAchievementState::Result)
            .onErrorReturn(EReportAchievementState::Error)
            .toFlowable()
            .startWith(EReportAchievementState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getEReportCurrentScore(
        token: String,
        userId: Int,
        callback: MutableLiveData<EReportCurrentScoreState>
    ) {
        apiService.getEReportCurrentScore(token, userId)
            .map<EReportCurrentScoreState>(EReportCurrentScoreState::Result)
            .onErrorReturn(EReportCurrentScoreState::Error)
            .toFlowable()
            .startWith(EReportCurrentScoreState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override suspend fun insertQuestion(data: List<QuestionEntity>, callback: MutableLiveData<Int>) {
        throw UnsupportedOperationException()
    }

    override suspend fun updateQuestion(data: QuestionEntity, callback: MutableLiveData<Int>) {
        throw UnsupportedOperationException()
    }

    override suspend fun clearQuestion(callback: MutableLiveData<Int>) {
        throw UnsupportedOperationException()
    }

    override fun getIsPengunjung(): Int {
        throw UnsupportedOperationException()
    }

    override fun updatePhoto(photo: String) {
        throw UnsupportedOperationException()
    }

    override fun updateEmail(email: String) {
        throw UnsupportedOperationException()
    }

    override fun setIsLogin(isLogin: Boolean) {
        throw UnsupportedOperationException()
    }

    override fun setPosition(position: Int) {
        throw UnsupportedOperationException()
    }

    override fun setClassId(id: Int) {
        throw UnsupportedOperationException()
    }

    override fun getPosition(): Int {
        throw UnsupportedOperationException()
    }

    override fun getClassId(): Int {
        throw UnsupportedOperationException()
    }

    override fun getTingkatId(): Int {
        throw UnsupportedOperationException()
    }

    override fun setIsTabClass(isTab: Boolean) {
        throw UnsupportedOperationException()
    }

    override fun getIsTabClass(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun getUserId(): Int {
        throw UnsupportedOperationException()
    }

    override fun getToken(): String {
        throw UnsupportedOperationException()
    }

    override fun getNis(): String {
        throw UnsupportedOperationException()
    }

    override fun getName(): String {
        throw UnsupportedOperationException()
    }

    override fun getKelas(): String {
        throw UnsupportedOperationException()
    }

    override fun getSubjectClass(): String {
        throw UnsupportedOperationException()
    }

    override fun getJenjang(): String {
        throw UnsupportedOperationException()
    }

    override fun getEmail(): String {
        throw UnsupportedOperationException()
    }

    override fun getPhoto(): String {
        throw UnsupportedOperationException()
    }

    override fun getStatus(): String {
        throw UnsupportedOperationException()
    }

    override fun setDataUser(data: com.alirsyad.app.data.model.user.DataUser) {
        throw UnsupportedOperationException()
    }

    override fun setIntro(isIntro: Boolean) {
        throw UnsupportedOperationException()
    }

    override fun getIsLogin(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun getIsIntro(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun logOut() {
        throw UnsupportedOperationException()
    }

    override fun getDisposable(): CompositeDisposable {
        return disposable
    }

    override fun getDatabase(): RoomDB {
        throw UnsupportedOperationException()
    }
}