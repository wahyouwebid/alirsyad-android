package com.alirsyad.app.data.model.summaryoflevel


import com.google.gson.annotations.SerializedName

data class SummaryOfLevel(
    @SerializedName("percentage")
    val percentage: Float?,
    @SerializedName("tingkat_kesulitan")
    val tingkatKesulitan: String?,
    @SerializedName("total_benar")
    val totalBenar: Int?,
    @SerializedName("total_terjawab")
    val totalTerjawab: Int?
)