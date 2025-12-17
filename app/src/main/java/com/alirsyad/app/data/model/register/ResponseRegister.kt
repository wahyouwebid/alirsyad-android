package com.alirsyad.app.data.model.register


import com.google.gson.annotations.SerializedName

data class ResponseRegister(
    @SerializedName("data")
    val data: UserRegister,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)