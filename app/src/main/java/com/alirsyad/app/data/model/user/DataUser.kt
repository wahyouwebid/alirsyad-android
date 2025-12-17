package com.alirsyad.app.data.model.user

import com.google.gson.annotations.SerializedName

data class DataUser(
    @SerializedName("expires_at")
    val expiresAt: String,

    @SerializedName("kelas")
    val kelas: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("nis")
    val nis: String,

    @SerializedName("photo")
    val photo: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("role")
    val role: String,

    @SerializedName("tingkat")
    val tingkat: String,

    @SerializedName("tingkat_id")
    val tingkatId: Int,

    @SerializedName("token")
    val token: String,

    @SerializedName("jenjang")
    val jenjang: String,

    @SerializedName("is_pengunjung")
    val isPengunjung: Int,

    @SerializedName("status")
    val status: String,

    @SerializedName("user_id")
    val userId: Int
)