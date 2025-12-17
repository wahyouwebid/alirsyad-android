package com.alirsyad.app.data.model.register.error


import com.google.gson.annotations.SerializedName

data class ErrorRegistered(
    @SerializedName("email")
    val email: List<String>?,
    @SerializedName("password")
    val password: List<String>?,
    @SerializedName("phone")
    val phone: List<String>?
)