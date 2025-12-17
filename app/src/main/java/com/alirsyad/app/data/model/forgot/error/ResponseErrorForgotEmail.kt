package com.alirsyad.app.data.model.forgot.error


import com.google.gson.annotations.SerializedName

data class ResponseErrorForgotEmail(
    @SerializedName("message")
    val message: Message?,
    @SerializedName("status")
    val status: Boolean?
)