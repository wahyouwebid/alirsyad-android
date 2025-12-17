package com.alirsyad.app.data.model.achievement


import com.google.gson.annotations.SerializedName

data class Simulasi(
    @SerializedName("id")
    val id: Int,
    @SerializedName("cover_url")
    val coverUrl: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("last_played")
    val lastPlayed: String?,
    @SerializedName("rata_rata_score")
    val rataRataScore: Double,
    @SerializedName("simulasi_url")
    val simulasiUrl: String,
    @SerializedName("last_score")
    val lastScore: LastScore?,
)