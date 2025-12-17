package com.alirsyad.app.data.model.error


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("error")
    val error: String
)