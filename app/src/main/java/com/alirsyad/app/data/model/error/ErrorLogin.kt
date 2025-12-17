package com.alirsyad.app.data.model.error


import com.google.gson.annotations.SerializedName

data class ErrorLogin(
    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)