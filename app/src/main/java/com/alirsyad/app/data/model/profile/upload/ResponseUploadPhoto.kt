package com.alirsyad.app.data.model.profile.upload


import com.google.gson.annotations.SerializedName

data class ResponseUploadPhoto(
    @SerializedName("data")
    val data: DataUpload,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)