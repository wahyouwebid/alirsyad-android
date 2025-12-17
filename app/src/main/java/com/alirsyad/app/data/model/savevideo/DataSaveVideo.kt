package com.alirsyad.app.data.model.savevideo


import com.google.gson.annotations.SerializedName

data class DataSaveVideo(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("semester")
    val semester: Int,
    @SerializedName("siswa_id")
    val siswaId: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("video_id")
    val videoId: Int
)