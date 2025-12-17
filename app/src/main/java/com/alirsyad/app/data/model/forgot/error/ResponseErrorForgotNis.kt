package com.alirsyad.app.data.model.forgot.error


import com.google.gson.annotations.SerializedName

data class ResponseErrorForgotNis(
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)