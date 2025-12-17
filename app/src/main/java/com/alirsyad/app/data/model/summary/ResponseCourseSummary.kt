package com.alirsyad.app.data.model.summary


import com.google.gson.annotations.SerializedName

data class ResponseCourseSummary(
    @SerializedName("data")
    val data: com.alirsyad.app.data.model.summary.DataSummary,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)