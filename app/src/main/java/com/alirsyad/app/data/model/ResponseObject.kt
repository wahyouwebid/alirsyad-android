package com.alirsyad.app.data.model

import com.google.gson.annotations.SerializedName

data class ResponseObject<T>(
    @SerializedName("data")
    val data: T,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)