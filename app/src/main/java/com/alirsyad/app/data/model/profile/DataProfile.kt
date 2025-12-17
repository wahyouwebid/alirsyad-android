package com.alirsyad.app.data.model.profile


import com.google.gson.annotations.SerializedName

data class DataProfile(
    @SerializedName("jenjang")
    val jenjang: String,
    @SerializedName("kelas")
    val kelas: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("nis")
    val nis: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("tingkat")
    val tingkat: String?
)