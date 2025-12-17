package com.alirsyad.app.data.model.register


import com.google.gson.annotations.SerializedName

data class UserRegister(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_pengunjung")
    val isPengunjung: Boolean,
    @SerializedName("jenjang_id")
    val jenjangId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("nis")
    val nis: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("username")
    val username: String
)