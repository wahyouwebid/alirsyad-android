package com.alirsyad.app.data.model.adaptive


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CheckAnswer(
    @SerializedName("next_paket_soal_id")
    val nextPaketSoalId: Int?,
    @SerializedName("next_tingkat_kesulitan")
    val nextTingkatKesulitan: String?,
    @SerializedName("score")
    val score: Int?,
    @SerializedName("status")
    val status: String?
): Parcelable