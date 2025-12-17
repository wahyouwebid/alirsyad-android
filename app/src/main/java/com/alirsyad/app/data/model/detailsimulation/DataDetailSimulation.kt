package com.alirsyad.app.data.model.detailsimulation


import com.google.gson.annotations.SerializedName

data class DataDetailSimulation(
    @SerializedName("bintang_score")
    val bintangScore: Int,
    @SerializedName("cover_url")
    val coverUrl: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("deleted_at")
    val deletedAt: Any,
    @SerializedName("description")
    val description: Any,
    @SerializedName("first_score")
    val firstScore: Any,
    @SerializedName("icon")
    val icon: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_score")
    val lastScore: Any,
    @SerializedName("mata_pelajaran")
    val mataPelajaran: com.alirsyad.app.data.model.detailsimulation.MataPelajaran,
    @SerializedName("mata_pelajaran_id")
    val mataPelajaranId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("next")
    val next: Any,
    @SerializedName("path_simulasi")
    val pathSimulasi: String,
    @SerializedName("played")
    val played: Boolean,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("rata_rata_score")
    val rataRataScore: Double,
    @SerializedName("scores")
    val scores: List<Any>,
    @SerializedName("semester")
    val semester: Int,
    @SerializedName("simulasi_url")
    val simulasiUrl: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("slug_url")
    val slugUrl: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("uploader_id")
    val uploaderId: Int,
    @SerializedName("urutan")
    val urutan: Int
)