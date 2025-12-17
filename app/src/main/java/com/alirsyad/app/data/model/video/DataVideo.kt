package com.alirsyad.app.data.model.video


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
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
    val mataPelajaran: com.alirsyad.app.data.model.video.MataPelajaran?,
    @SerializedName("mata_pelajaran_id")
    val mataPelajaranId: Int?,
    @SerializedName("name")
    val name: String,
    @SerializedName("next")
    val next: com.alirsyad.app.data.model.module.Next?,
    @SerializedName("previous")
    val previous: com.alirsyad.app.data.model.module.Previous?,
    @SerializedName("semester")
    val semester: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("uploader")
    val uploader: com.alirsyad.app.data.model.video.Uploader?,
    @SerializedName("uploader_id")
    val uploaderId: Int,
    @SerializedName("video_url")
    val videoUrl: String,
    @SerializedName("watched")
    val watched: Boolean,
    @SerializedName("youtubeId")
    val youtubeId: String,
    @SerializedName("is_public")
    val isPublic: Int = 1,
    @SerializedName("mapel_assigned")
    val mapelAssigned: Int = 0
) : Parcelable