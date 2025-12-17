package com.alirsyad.app.data.model.ereport


import com.google.gson.annotations.SerializedName

data class Nilai(
    @SerializedName("tingkat_kesulitan")
    val tingkatKesulitan: String?,
    @SerializedName("total_benar")
    val totalBenar: Int?,
    @SerializedName("total_terjawab")
    val totalTerjawab: Int?,
    @SerializedName("percentage")
    val percentage: Float?
)