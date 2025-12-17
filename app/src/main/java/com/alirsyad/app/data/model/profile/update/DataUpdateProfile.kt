package com.alirsyad.app.data.model.profile.update


import com.google.gson.annotations.SerializedName

data class DataUpdateProfile(
    @SerializedName("email")
    val email: String,
    @SerializedName("jenjang")
    val jenjang: String,
    @SerializedName("kelas")
    val kelas: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nis")
    val nis: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("tingkat")
    val tingkat: String
)