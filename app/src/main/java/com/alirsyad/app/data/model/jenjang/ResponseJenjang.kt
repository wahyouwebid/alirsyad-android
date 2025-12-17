package com.alirsyad.app.data.model.jenjang


import com.google.gson.annotations.SerializedName

data class ResponseJenjang(
    @SerializedName("data")
    val data: List<com.alirsyad.app.data.model.jenjang.Jenjang>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)