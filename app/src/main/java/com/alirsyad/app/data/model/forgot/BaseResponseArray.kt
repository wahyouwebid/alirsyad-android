package com.alirsyad.app.data.model.forgot


import com.google.gson.annotations.SerializedName

data class BaseResponseArray(
    @SerializedName("data")
    val data: List<Any>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)