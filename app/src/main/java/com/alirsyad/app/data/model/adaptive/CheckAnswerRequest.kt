package com.alirsyad.app.data.model.adaptive


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CheckAnswerRequest(
    @SerializedName("paket_soal_id")
    val paketSoalId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("list_soal")
    val listSoal: List<AnswerSelected>,
): Parcelable

@Parcelize
data class PreviewRequest(
    @SerializedName("paket_soal_id")
    val paketSoalId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("list_soal")
    val listSoal: List<ListAnswer>,
): Parcelable

@Parcelize
data class ListAnswer(
    @SerializedName("id")
    var id: Int,
    @SerializedName("jawaban")
    var jawaban: String?,
    @SerializedName("list_jawaban")
    var listJawaban: List<String?>,
): Parcelable
