package com.alirsyad.app.data.model.ereport


import com.google.gson.annotations.SerializedName

data class EReportScore(
    @SerializedName("nama")
    val nama: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("subbab")
    val subbab: List<Subbab>
)