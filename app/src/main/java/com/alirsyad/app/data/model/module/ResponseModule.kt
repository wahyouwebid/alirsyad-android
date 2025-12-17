package com.alirsyad.app.data.model.module


import com.google.gson.annotations.SerializedName

data class ResponseModule(
    @SerializedName("data")
    val data: List<com.alirsyad.app.data.model.module.DataModule>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)