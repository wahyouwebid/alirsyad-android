package com.alirsyad.app.data.model.detailmodule


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataModule(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("deleted_at")
    val deletedAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("mata_pelajaran")
    val mataPelajaran: MataPelajaran,
    @SerializedName("mata_pelajaran_id")
    val mataPelajaranId: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("next")
    val next: Next?,
    @SerializedName("pdf_path")
    val pdfPath: String?,
    @SerializedName("pdf_url")
    val pdfUrl: String?,
    @SerializedName("pdf_viewer")
    val pdfViewer: String?,
    @SerializedName("previous")
    val previous: com.alirsyad.app.data.model.module.Previous?,
    @SerializedName("read")
    val read: Boolean?,
    @SerializedName("semester")
    val semester: Int?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("uploader_id")
    val uploaderId: Int?
): Parcelable