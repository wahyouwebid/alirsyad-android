package com.alirsyad.app.data.model.profile


import com.google.gson.annotations.SerializedName

data class ResponseProfile(
    @SerializedName("data")
    val data: com.alirsyad.app.data.model.profile.DataProfile,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)