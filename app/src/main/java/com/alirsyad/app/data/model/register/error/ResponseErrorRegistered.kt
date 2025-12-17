package com.alirsyad.app.data.model.register.error


import com.google.gson.annotations.SerializedName

data class ResponseErrorRegistered(
    @SerializedName("data")
    val data: ErrorRegistered?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)