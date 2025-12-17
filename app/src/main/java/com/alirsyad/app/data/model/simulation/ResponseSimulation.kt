package com.alirsyad.app.data.model.simulation


import com.google.gson.annotations.SerializedName

data class ResponseSimulation(
    @SerializedName("data")
    val data: List<DataSimulation>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)