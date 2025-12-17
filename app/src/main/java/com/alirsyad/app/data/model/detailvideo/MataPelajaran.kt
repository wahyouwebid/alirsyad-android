package com.alirsyad.app.data.model.detailvideo


import com.google.gson.annotations.SerializedName

data class MataPelajaran(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("deleted_at")
    val deletedAt: String?,
    @SerializedName("disabled")
    val disabled: Boolean,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("tingkat")
    val tingkat: com.alirsyad.app.data.model.detailvideo.Tingkat,
    @SerializedName("tingkat_id")
    val tingkatId: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)