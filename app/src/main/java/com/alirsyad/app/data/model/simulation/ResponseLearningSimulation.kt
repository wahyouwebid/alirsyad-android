package com.alirsyad.app.data.model.simulation


import com.google.gson.annotations.SerializedName

data class ResponseLearningSimulation(
    @SerializedName("data")
    val data: List<com.alirsyad.app.data.model.simulation.Simulasi>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)