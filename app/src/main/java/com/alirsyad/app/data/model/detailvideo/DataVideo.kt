package com.alirsyad.app.data.model.detailvideo


import com.google.gson.annotations.SerializedName

data class DataVideo(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("deleted_at")
    val deletedAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("mata_pelajaran")
    val mataPelajaran: MataPelajaran,
    @SerializedName("mata_pelajaran_id")
    val mataPelajaranId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("next")
    val next: com.alirsyad.app.data.model.detailmodule.Next?,
    @SerializedName("previous")
    val previous: com.alirsyad.app.data.model.module.Previous?,
    @SerializedName("semester")
    val semester: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("uploader_id")
    val uploaderId: Int,
    @SerializedName("video_url")
    val videoUrl: String,
    @SerializedName("watched")
    val watched: Boolean,
    @SerializedName("youtubeId")
    val youtubeId: String
)