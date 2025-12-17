package com.alirsyad.app.data.model.detailvideo


import com.google.gson.annotations.SerializedName

data class ResponseDetailVideo(
    @SerializedName("data")
    val data: com.alirsyad.app.data.model.detailvideo.DataVideo,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)