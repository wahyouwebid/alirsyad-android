package com.alirsyad.app.data.model.user

import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("data")
    val data: com.alirsyad.app.data.model.user.DataUser,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)