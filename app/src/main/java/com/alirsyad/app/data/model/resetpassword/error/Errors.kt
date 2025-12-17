package com.alirsyad.app.data.model.resetpassword.error


import com.google.gson.annotations.SerializedName

data class Errors(
    @SerializedName("password")
    val password: List<String>?
)