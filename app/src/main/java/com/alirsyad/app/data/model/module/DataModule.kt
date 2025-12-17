package com.alirsyad.app.data.model.module

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataModule(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("pdf_path")
    val pdfPath: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("icon")
    val icon: String,

    @SerializedName("mata_pelajaran_id")
    val mataPelajaranId: Int,

    @SerializedName("uploader_id")
    val uploaderId: Int,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("deleted_at")
    val deletedAt: String?,

    @SerializedName("slug")
    val slug: String,

    @SerializedName("semester")
    val semester: Int,

    @SerializedName("read")
    val read: Boolean,

    @SerializedName("pdf_url")
    val pdfUrl: String,

    @SerializedName("previous")
    val previous: Previous,

    @SerializedName("next")
    val next: Next?,

    @SerializedName("mata_pelajaran")
    val mataPelajaran: MataPelajaran,

    @SerializedName("is_public")
    val isPublic: Int = 1,

    @SerializedName("mapel_assigned")
    val mapelAssigned: Int = 0
) : Parcelable