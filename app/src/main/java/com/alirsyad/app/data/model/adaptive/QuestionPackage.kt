package com.alirsyad.app.data.model.adaptive


import com.google.gson.annotations.SerializedName

data class QuestionPackage(
    @SerializedName("bab_id")
    val babId: Int?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("creator_id")
    val creatorId: String?,
    @SerializedName("deleted_at")
    val deletedAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("is_active")
    val isActive: Int?,
    @SerializedName("judul_subbab")
    val judulSubbab: String?,
    @SerializedName("jumlah_publish")
    val jumlahPublish: Int?,
    @SerializedName("mata_pelajaran_id")
    val mataPelajaranId: Int?,
    @SerializedName("nilai_kkm")
    val nilaiKkm: Int?,
    @SerializedName("subbab")
    val subbab: Int?,
    @SerializedName("tingkat_kesulitan")
    val tingkatKesulitan: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)