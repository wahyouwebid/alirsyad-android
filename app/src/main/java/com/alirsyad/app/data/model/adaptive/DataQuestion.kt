package com.alirsyad.app.data.model.adaptive


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataQuestion(
    @SerializedName("list_soal")
    val listSoal: List<Question>,
    @SerializedName("paket_soal_id")
    val paketSoalId: Int?,
    @SerializedName("tingkat_kesulitan")
    val tingkatKesulitan: String?,
): Parcelable