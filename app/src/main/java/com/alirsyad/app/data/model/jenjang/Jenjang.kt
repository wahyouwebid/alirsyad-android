package com.alirsyad.app.data.model.jenjang


import com.google.gson.annotations.SerializedName

data class  Jenjang(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("name")
    val name: String
)