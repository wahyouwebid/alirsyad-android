package com.alirsyad.app.data.model.tingkat

import com.alirsyad.app.data.model.courses.Tingkat
import com.google.gson.annotations.SerializedName

data class ResponseTingkat(
    @SerializedName("data")
    val data: List<Tingkat>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)