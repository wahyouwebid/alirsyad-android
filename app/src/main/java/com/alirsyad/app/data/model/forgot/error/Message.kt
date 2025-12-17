package com.alirsyad.app.data.model.forgot.error


import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("email")
    val email: List<String>?
)