package com.alirsyad.app.data.model.ereport


import com.google.gson.annotations.SerializedName

data class Sulit(
    @SerializedName("percentage")
    val percentage: Float?,
    @SerializedName("total_benar")
    val totalBenar: Int?,
    @SerializedName("total_terjawab")
    val totalTerjawab: Int?
)