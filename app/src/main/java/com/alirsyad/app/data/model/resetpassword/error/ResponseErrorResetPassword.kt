package com.alirsyad.app.data.model.resetpassword.error


import com.google.gson.annotations.SerializedName

data class ResponseErrorResetPassword(
    @SerializedName("errors")
    val errors: Errors?,
    @SerializedName("message")
    val message: String?
)