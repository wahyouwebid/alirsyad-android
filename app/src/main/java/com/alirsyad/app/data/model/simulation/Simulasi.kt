package com.alirsyad.app.data.model.simulation


import android.os.Parcelable
import com.alirsyad.app.data.model.module.Previous
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Simulasi(
    @SerializedName("bintang_score")
    val bintangScore: Float,
    @SerializedName("cover_url")
    val coverUrl: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("deleted_at")
    val deletedAt: String?,
    @SerializedName("description")
    val description: String,
    @SerializedName("first_score")
    val firstScore: FirstScore,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_score")
    val lastScore: LastScore,
    @SerializedName("mata_pelajaran")
    val mataPelajaran: MataPelajaranX,
    @SerializedName("mata_pelajaran_id")
    val mataPelajaranId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("next")
    val next: Next,
    @SerializedName("path_simulasi")
    val pathSimulasi: String,
    @SerializedName("played")
    val played: Boolean,
    @SerializedName("previous")
    val previous: Previous?,
    @SerializedName("rata_rata_score")
    val rataRataScore: Double,
    @SerializedName("scores")
    val scores: List<Score>,
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
    @SerializedName("is_public")
    val isPublic: Int = 1,
    @SerializedName("mapel_assigned")
    val mapelAssigned: Int = 0
) : Parcelable