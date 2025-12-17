package com.alirsyad.app.data.model.detailsimulation


import com.google.gson.annotations.SerializedName

data class ResponseDetailSimulation(
    @SerializedName("data")
    val data: com.alirsyad.app.data.model.detailsimulation.DataDetailSimulation,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)