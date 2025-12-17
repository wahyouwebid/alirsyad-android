package com.alirsyad.app.data.repository

import androidx.lifecycle.MutableLiveData
import com.alirsyad.app.data.database.RoomDB
import com.alirsyad.app.data.entity.QuestionEntity
import com.alirsyad.app.data.model.user.DataUser
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

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

interface Repository {

    fun login(
        body: JsonObject,
        callback: MutableLiveData<LoginState>
    )

    fun getHomeBanner(
        token: String,
        callback: MutableLiveData<BannerState>
    )

    fun getUpdate(
        token: String,
        callback: MutableLiveData<UpdateState>
    )

    fun getSubjectClass(
        token: String,
        callback: MutableLiveData<TingkatState>
    )

    fun getCourse(
        token: String,
        callback: MutableLiveData<CourseState>
    )

    fun getCourseDetail(
        token: String,
        id: Int,
        callback: MutableLiveData<DetailCourseState>
    )

    fun getCourseInProgress(
        token: String,
        callback: MutableLiveData<CourseState>
    )

    fun getCourseUpComing(
        token: String,
        callback: MutableLiveData<CourseState>
    )

    fun getCourseDone(
        token: String,
        callback: MutableLiveData<CourseState>
    )

    fun getMapelByClass(
        token: String,
        id: Int,
        callback: MutableLiveData<CourseState>
    )

    fun getCourseSummary(
        token: String,
        id : Int,
        callback: MutableLiveData<CourseSummaryState>
    )

    fun getCourseModule(
        token: String,
        id : Int,
        callback: MutableLiveData<CourseModuleState>
    )

    fun getCourseVideo(
        token: String,
        id : Int,
        callback: MutableLiveData<CourseVideoState>
    )

    fun getCourseSimulation(
        token: String,
        id : Int,
        callback: MutableLiveData<CourseSimulationState>
    )

    fun getSimulation(
        token: String,
        callback: MutableLiveData<SimulationState>
    )

    fun getDetailModule(
        token: String,
        id : Int,
        callback: MutableLiveData<DetailModuleState>
    )

    fun getDetailVideo(
        token: String,
        id : Int,
        callback: MutableLiveData<DetailVideoState>
    )

    fun getDetailSimulation(
        token: String,
        id : Int,
        callback: MutableLiveData<DetailSimulationState>
    )

    fun saveFlagModule(
        token: String,
        id : Int,
        callback: MutableLiveData<SaveModuleState>
    )

    fun saveFlagVideo(
        token: String,
        id : Int,
        callback: MutableLiveData<SaveVideoState>
    )

    fun getProfile(
        token: String,
        callback: MutableLiveData<ProfileState>
    )

    fun updateProfile(
        token: String,
        request: JsonObject,
        callback: MutableLiveData<UpdateProfileState>
    )

    fun updatePassword(
        token: String,
        request: JsonObject,
        callback: MutableLiveData<UpdateProfileState>
    )

    fun uploadPhoto(
        token: String,
        file: MultipartBody.Part,
        callback: MutableLiveData<UploadPhotoState>
    )

    fun getListJenjang(
        callback: MutableLiveData<JenjangState>
    )

    fun register(
        request: JsonObject,
        callback: MutableLiveData<RegisterState>
    )

    fun getCourseActive(
        token: String,
        callback: MutableLiveData<CourseState>
    )

    fun getCourseNonActive(
        token: String,
        callback: MutableLiveData<CourseState>
    )

    fun forgotPassword(
        request: JsonObject,
        callback: MutableLiveData<CommonState>
    )

    fun forgotPasswordStudent(
        request: JsonObject,
        callback: MutableLiveData<CommonState>
    )

    fun resetPassword(
        request: JsonObject,
        callback: MutableLiveData<CommonState>
    )

    fun getQuestionPackage(
        token: String,
        mataPelajaranId: Int,
        callback: MutableLiveData<ResponseArrayState>
    )

    fun getQuestion(
        token: String,
        paketSoalId: Int,
        callback: MutableLiveData<QuestionState>
    )

    fun getLevel(
        token: String,
        paketSoalId: Int,
        siswaId: Int,
        callback: MutableLiveData<LevelState>
    )

    fun checkAnswer(
        token: String,
        request: JsonObject,
        callback: MutableLiveData<CheckAnswerState>
    )

    fun getPreviewAdaptive(
        token: String,
        request: JsonObject,
        callback: MutableLiveData<PreviewAdaptiveState>
    )

    fun getAchievementSimulation(
        token: String,
        courseId: Int,
        callback: MutableLiveData<AchievementSimulationState>
    )

    fun getEReportYourScore(
        token: String,
        userId: Int,
        courseId: Int,
        callback: MutableLiveData<EReportScoreState>
    )

    fun getEReportChart(
        token: String,
        levelId: Int,
        userId: Int,
        callback: MutableLiveData<EReportChartState>
    )

    fun getSummaryOfLevel(
        token: String,
        courseId: Int,
        userId: Int,
        callback: MutableLiveData<SummaryOfLevelState>
    )

    fun getEReportAchievement(
        token: String,
        levelId: Int,
        userId: Int,
        callback: MutableLiveData<EReportAchievementState>
    )

    fun getEReportCurrentScore(
        token: String,
        userId: Int,
        callback: MutableLiveData<EReportCurrentScoreState>
    )

    fun getQuestion(callback : MutableLiveData<List<QuestionEntity>>)

    suspend fun insertQuestion(data: List<QuestionEntity>, callback: MutableLiveData<Int>)

    suspend fun updateQuestion(data : QuestionEntity, callback : MutableLiveData<Int>)

    suspend fun clearQuestion(callback : MutableLiveData<Int>)

    fun getIsPengunjung(): Int

    fun updatePhoto(photo: String)

    fun updateEmail(email: String)

    fun setIsLogin(isLogin: Boolean)

    fun setPosition(position: Int)

    fun setClassId(id: Int)

    fun getToken(): String
    fun getNis(): String
    fun getName(): String
    fun getKelas(): String
    fun getSubjectClass(): String
    fun getJenjang(): String
    fun getEmail(): String
    fun getPhoto(): String
    fun getStatus(): String
    fun setDataUser(data: DataUser)
    fun setIntro(isIntro: Boolean)
    fun getIsLogin(): Boolean
    fun getIsIntro(): Boolean
    fun getPosition(): Int
    fun getClassId(): Int
    fun getTingkatId(): Int
    fun setIsTabClass(isTab: Boolean)
    fun getIsTabClass(): Boolean
    fun getUserId(): Int
    fun logOut()
    fun getDisposable(): CompositeDisposable
    fun getDatabase() : RoomDB
}