package com.alirsyad.app.data.model.common

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("success")
    val success: Boolean = false,

    @SerializedName("message")
    val message: String?,
)
