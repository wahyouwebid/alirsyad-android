package com.alirsyad.app.data.model.banner

import com.google.gson.annotations.SerializedName

data class ResponseBanner(
    @SerializedName("data")
    val data: List<DataBanner>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)