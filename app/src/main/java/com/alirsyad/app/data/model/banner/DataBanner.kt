package com.alirsyad.app.data.model.banner

import com.google.gson.annotations.SerializedName

data class DataBanner(
    @SerializedName("id") var id: Int = -1,
    @SerializedName("title") var title: String = "",
    @SerializedName("description") var description: String = "",
    @SerializedName("image") var image: String = "",
    @SerializedName("file") var file: String = "",
    @SerializedName("urutan") var urutan: Int? = null,
    @SerializedName("activeStatus") var activeStatus: Int? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("deleted_at") var deletedAt: String? = null
)