package com.alirsyad.app.data.model.achievement


import com.google.gson.annotations.SerializedName

data class LastScore(
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("percentage")
    val percentage: Float?,
    @SerializedName("total")
    val total: Int?
)