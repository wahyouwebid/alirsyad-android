package com.alirsyad.app.data.model.courses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kelas(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("deleted_at")
    val deletedAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("tingkat")
    val tingkat: com.alirsyad.app.data.model.courses.Tingkat?,
    @SerializedName("tingkat_id")
    val tingkatId: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("wali_kelas_id")
    val waliKelasId: Int
) : Parcelable