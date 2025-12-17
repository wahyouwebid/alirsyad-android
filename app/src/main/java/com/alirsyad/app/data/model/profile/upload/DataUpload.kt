package com.alirsyad.app.data.model.profile.upload


import com.google.gson.annotations.SerializedName

data class DataUpload(
    @SerializedName("image_path")
    val imagePath: String,
    @SerializedName("image_url")
    val imageUrl: String
)