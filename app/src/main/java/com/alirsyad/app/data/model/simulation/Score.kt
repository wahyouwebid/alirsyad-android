package com.alirsyad.app.data.model.simulation


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Score(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("deleted_at")
    val deletedAt: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("jenjang")
    val jenjang: Int,
    @SerializedName("kelas")
    val kelas: Int,
    @SerializedName("kelas_id")
    val kelasId: Int?,
    @SerializedName("nama_pengajar")
    val namaPengajar: String,
    @SerializedName("pengajar_id")
    val pengajarId: Int,
    @SerializedName("percobaan_ke")
    val percobaanKe: Int,
    @SerializedName("score")
    val score: Int,
    @SerializedName("semester")
    val semester: Int,
    @SerializedName("simulasi_id")
    val simulasiId: Int,
    @SerializedName("siswa_id")
    val siswaId: Int,
    @SerializedName("tingkat")
    val tingkat: Int,
    @SerializedName("updated_at")
    val updatedAt: String
) : Parcelable