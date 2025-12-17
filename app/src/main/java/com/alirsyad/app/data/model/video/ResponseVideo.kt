package com.alirsyad.app.data.model.video


import com.google.gson.annotations.SerializedName

data class ResponseVideo(
    @SerializedName("data")
    val data: List<DataVideo>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)