package com.alirsyad.app.data.model.ereport


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EReportCurrentScore(
    @SerializedName("last_updated")
    val lastUpdated: String?,
    @SerializedName("mata_pelajaran_icon")
    val mataPelajaranIcon: String?,
    @SerializedName("mata_pelajaran_name")
    val mataPelajaranName: String?,
    @SerializedName("tingkat_kesulitan")
    val tingkatKesulitan: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("total_benar")
    val totalBenar: Int?,
    @SerializedName("total_terjawab")
    val totalTerjawab: Int?
): Parcelable