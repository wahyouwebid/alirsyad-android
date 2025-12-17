package com.alirsyad.app.data.model

import com.google.gson.annotations.SerializedName

data class ResponseArrayObject<T>(
    @SerializedName("data")
    val data: List<T>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)