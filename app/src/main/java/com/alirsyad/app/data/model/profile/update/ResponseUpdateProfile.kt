package com.alirsyad.app.data.model.profile.update


import com.google.gson.annotations.SerializedName

data class ResponseUpdateProfile(
    @SerializedName("data")
    val data: com.alirsyad.app.data.model.profile.update.DataUpdateProfile,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)