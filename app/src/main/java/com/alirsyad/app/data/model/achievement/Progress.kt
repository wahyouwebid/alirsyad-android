package com.alirsyad.app.data.model.achievement


import com.google.gson.annotations.SerializedName

data class Progress(
    @SerializedName("done")
    val done: Int?,
    @SerializedName("percentage")
    val percentage: Float?,
    @SerializedName("total")
    val total: Int?
)