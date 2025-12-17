package com.alirsyad.app.data.model.level

import com.google.gson.annotations.SerializedName

data class ResponseLevel(
    @SerializedName("data")
    val data: List<Level>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)