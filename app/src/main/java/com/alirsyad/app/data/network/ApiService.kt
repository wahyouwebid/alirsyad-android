package com.alirsyad.app.data.network

import com.alirsyad.app.data.model.ResponseArrayObject
import com.alirsyad.app.data.model.ResponseObject
import com.alirsyad.app.data.model.achievement.Achievement
import com.alirsyad.app.data.model.adaptive.AdaptiveModule
import com.alirsyad.app.data.model.adaptive.CheckAnswer
import com.alirsyad.app.data.model.adaptive.DataQuestion
import com.alirsyad.app.data.model.banner.ResponseBanner
import com.alirsyad.app.data.model.courses.ResponseCourseDetail
import com.alirsyad.app.data.model.courses.ResponseCourses
import com.alirsyad.app.data.model.detailmodule.ResponseDetailModule
import com.alirsyad.app.data.model.detailsimulation.ResponseDetailSimulation
import com.alirsyad.app.data.model.detailvideo.ResponseDetailVideo
import com.alirsyad.app.data.model.ereport.EReportAchievement
import com.alirsyad.app.data.model.ereport.EReportChart
import com.alirsyad.app.data.model.ereport.EReportCurrentScore
import com.alirsyad.app.data.model.ereport.EReportScore
import com.alirsyad.app.data.model.forgot.BaseResponseArray
import com.alirsyad.app.data.model.jenjang.ResponseJenjang
import com.alirsyad.app.data.model.level.Level
import com.alirsyad.app.data.model.module.ResponseModule
import com.alirsyad.app.data.model.previewadaptive.PreviewAdaptiveResponse
import com.alirsyad.app.data.model.profile.ResponseProfile
import com.alirsyad.app.data.model.profile.update.ResponseUpdateProfile
import com.alirsyad.app.data.model.profile.upload.ResponseUploadPhoto
import com.alirsyad.app.data.model.register.ResponseRegister
import com.alirsyad.app.data.model.savemodule.ResponseSaveModule
import com.alirsyad.app.data.model.savevideo.ResponseSaveVideo
import com.alirsyad.app.data.model.simulation.ResponseLearningSimulation
import com.alirsyad.app.data.model.simulation.ResponseSimulation
import com.alirsyad.app.data.model.summary.ResponseCourseSummary
import com.alirsyad.app.data.model.summaryoflevel.SummaryOfLevel
import com.alirsyad.app.data.model.tingkat.ResponseTingkat
import com.alirsyad.app.data.model.update.ResponseUpdate
import com.alirsyad.app.data.model.video.ResponseVideo
import com.google.gson.JsonObject
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

interface ApiService {

    @POST("login")
    fun getHeadline(
        @Body body: JsonObject
    ): Single<com.alirsyad.app.data.model.user.ResponseLogin>

    @Headers("Accept: application/json")
    @GET("home/banners")
    fun getHomeBanner(
        @Header("Authorization") token: String
    ): Single<ResponseBanner>

    @Headers("Accept: application/json")
    @GET("home/updates")
    fun getUpdate(
        @Header("Authorization") token: String
    ): Single<ResponseUpdate>

    @Headers("Accept: application/json")
    @GET("home/tingkats")
    fun getTingkat(
        @Header("Authorization") token: String
    ): Single<ResponseTingkat>

    @Headers("Accept: application/json")
    @GET("mata_pelajarans?")
    fun getCourseByTingkat(
        @Header("Authorization") token: String,
        @Query("q_tingkat_id") id: Int
    ): Single<ResponseCourses>

    @Headers("Accept: application/json")
    @GET("mata_pelajarans")
    fun getCourses(
        @Header("Authorization") token: String
    ): Single<ResponseCourses>

    @Headers("Accept: application/json")
    @GET("mata_pelajarans/inprogress")
    fun getCoursesInProgress(
        @Header("Authorization") token: String
    ): Single<ResponseCourses>

    @Headers("Accept: application/json")
    @GET("mata_pelajarans/upcoming")
    fun getCoursesUpComing(
        @Header("Authorization") token: String
    ): Single<ResponseCourses>

    @Headers("Accept: application/json")
    @GET("mata_pelajarans/passed")
    fun getCoursesDone(
        @Header("Authorization") token: String
    ): Single<ResponseCourses>

    @Headers("Accept: application/json")
    @GET("mata_pelajarans/{id}")
    fun getCourseDetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Single<ResponseCourseDetail>

    @Headers("Accept: application/json")
    @GET("mata_pelajarans/{id}/summary")
    fun getCourseSummary(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Single<ResponseCourseSummary>

    @Headers("Accept: application/json")
    @GET("moduls?")
    fun getCourseModule(
        @Header("Authorization") token: String,
        @Query("q_mata_pelajaran_id") id: Int
    ): Single<ResponseModule>

    @Headers("Accept: application/json")
    @GET("videos?")
    fun getCourseVideos(
        @Header("Authorization") token: String,
        @Query("q_mata_pelajaran_id") id: Int
    ): Single<ResponseVideo>

    @Headers("Accept: application/json")
    @GET("simulasis?")
    fun getCourseSimulation(
        @Header("Authorization") token: String,
        @Query("q_mata_pelajaran_id") id: Int
    ): Single<ResponseLearningSimulation>

    @Headers("Accept: application/json")
    @GET("nilai-simulasi")
    fun getSimulation(
        @Header("Authorization") token: String
    ): Single<ResponseSimulation>

    @Headers("Accept: application/json")
    @GET("moduls/{id}")
    fun getDetailModule(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Single<ResponseDetailModule>

    @Headers("Accept: application/json")
    @GET("videos/{id}")
    fun getDetailVideos(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Single<ResponseDetailVideo>

    @Headers("Accept: application/json")
    @GET("simulasis/{id}")
    fun getDetailSimulation(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Single<ResponseDetailSimulation>

    @Headers("Accept: application/json")
    @POST("moduls/{id}/flag")
    fun saveFlagModule(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Single<ResponseSaveModule>

    @Headers("Accept: application/json")
    @POST("videos/{id}/flag")
    fun saveFlagVideo(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Single<ResponseSaveVideo>

    @Headers("Accept: application/json")
    @GET("profile")
    fun getProfile(
        @Header("Authorization") token: String
    ): Single<ResponseProfile>

    @Headers("Accept: application/json")
    @POST("profile")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: JsonObject
    ): Single<ResponseUpdateProfile>

    @Headers("Accept: application/json")
    @POST("profile/password")
    fun updatePassword(
        @Header("Authorization") token: String,
        @Body request: JsonObject
    ): Single<ResponseUpdateProfile>

    @Multipart
    @POST("upload/photo")
    fun uploadPhoto(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
    ): Single<ResponseUploadPhoto>

    @Headers("Accept: application/json")
    @GET("jenjangs")
    fun getJenjang(): Single<ResponseJenjang>

    @POST("register")
    fun register(
        @Body request: JsonObject
    ): Single<ResponseRegister>

    @Headers("Accept: application/json")
    @GET("mata_pelajarans/active")
    fun getCoursesActive(
        @Header("Authorization") token: String
    ): Single<ResponseCourses>

    @Headers("Accept: application/json")
    @GET("mata_pelajarans/not-active")
    fun getCoursesNonActive(
        @Header("Authorization") token: String
    ): Single<ResponseCourses>

    @Headers("Accept: application/json")
    @POST("password/forgot")
    fun forgotPassword(
        @Body request: JsonObject
    ): Single<BaseResponseArray>

    @Headers("Accept: application/json")
    @POST("password/forgot/student")
    fun forgotPasswordStudent(
        @Body request: JsonObject
    ): Single<BaseResponseArray>

    @Headers("Accept: application/json")
    @POST("password/reset")
    fun resetPassword(
        @Body request: JsonObject
    ): Single<BaseResponseArray>

    @Headers("Accept: application/json")
    @GET("paket-soal")
    fun getQuestionPackage(
        @Header("Authorization") token: String,
        @Query("mata_pelajaran_id") mataPelajaranId: Int
    ): Single<ResponseArrayObject<AdaptiveModule>>

    @Headers("Accept: application/json")
    @GET("soal")
    fun getQuestion(
        @Header("Authorization") token: String,
        @Query("paket_soal_id") paketSoalId: Int
    ): Single<ResponseObject<DataQuestion>>

    @Headers("Accept: application/json")
    @POST("soal/check-answers")
    fun checkAnswer(
        @Header("Authorization") token: String,
        @Body request: JsonObject
    ): Single<ResponseObject<CheckAnswer>>

    @Headers("Accept: application/json")
    @POST("soal/studies")
    fun getPreviewQuestion(
        @Header("Authorization") token: String,
        @Body request: JsonObject
    ): Single<PreviewAdaptiveResponse>

    @Headers("Accept: application/json")
    @GET("nilai-simulasi/mapels/{mapel_id}")
    fun getAchievementSimulation(
        @Header("Authorization") token: String,
        @Path("mapel_id") mapelId: Int
    ): Single<ResponseObject<Achievement>>

    @Headers("Accept: application/json")
    @GET("e-raport")
    fun getEReportYourScore(
        @Header("Authorization") token: String,
        @Query("mata_pelajaran_id") matePelajaranId: Int,
        @Query("user_id") userId: Int,
    ): Single<ResponseArrayObject<EReportScore>>

    @Headers("Accept: application/json")
    @GET("e-raport/subject-achievement")
    fun getEReportChart(
        @Header("Authorization") token: String,
        @Query("tingkat_id") tingkatId: Int,
        @Query("user_id") userId: Int,
    ): Single<ResponseArrayObject<EReportChart>>

    @Headers("Accept: application/json")
    @GET("e-raport/current-score?limit=3")
    fun getEReportCurrentScore(
        @Header("Authorization") token: String,
        @Query("user_id") userId: Int,
    ): Single<ResponseArrayObject<EReportCurrentScore>>

    @Headers("Accept: application/json")
    @GET("e-raport/summary-of-level")
    fun getSummaryOfLevel(
        @Header("Authorization") token: String,
        @Query("mata_pelajaran_id") matePelajaranId: Int,
        @Query("user_id") userId: Int,
    ): Single<ResponseArrayObject<SummaryOfLevel>>

    @Headers("Accept: application/json")
    @GET("e-raport/home-achievement")
    fun getHomeAchievement(
        @Header("Authorization") token: String,
        @Query("tingkat_id") tingkatId: Int,
        @Query("user_id") userId: Int,
    ): Single<ResponseObject<EReportAchievement>>

    @Headers("Accept: application/json")
    @GET("paket-soal/checkpoint?")
    fun getLevel(
        @Header("Authorization") token: String,
        @Query("paket_soal_id") paketSoalId: Int,
        @Query("siswa_id") siswaId: Int,
    ): Single<ResponseArrayObject<Level>>

}