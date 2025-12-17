package com.alirsyad.app.data.model.update

import com.google.gson.annotations.SerializedName

data class ResponseUpdate(
    @SerializedName("data")
    val data: List<DataUpdate>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)