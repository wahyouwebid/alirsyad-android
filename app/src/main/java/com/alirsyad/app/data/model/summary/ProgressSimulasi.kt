package com.alirsyad.app.data.model.summary


import com.google.gson.annotations.SerializedName

data class ProgressSimulasi(
    @SerializedName("done")
    val done: Float,
    @SerializedName("percentage")
    val percentage: Float,
    @SerializedName("total")
    val total: Float
)