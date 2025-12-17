package com.alirsyad.app.data.model.resetpassword

import com.google.gson.annotations.SerializedName


data class ResetPasswordRequest (
    @SerializedName("email")
    val email: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("password_confirmation")
    val passwordConfirmation: String,
)