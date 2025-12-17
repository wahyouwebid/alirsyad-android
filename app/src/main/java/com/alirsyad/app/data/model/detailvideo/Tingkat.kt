package com.alirsyad.app.data.model.detailvideo


import com.google.gson.annotations.SerializedName

data class Tingkat(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("deleted_at")
    val deletedAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("jenjang")
    val jenjang: com.alirsyad.app.data.model.detailvideo.Jenjang,
    @SerializedName("jenjang_id")
    val jenjangId: Int,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("use_story_path")
    val useStoryPath: Int
)