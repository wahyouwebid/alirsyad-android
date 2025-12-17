package com.alirsyad.app.data.model.savevideo


import com.google.gson.annotations.SerializedName

data class ResponseSaveVideo(
    @SerializedName("data")
    val data: com.alirsyad.app.data.model.savevideo.DataSaveVideo,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)