package com.alirsyad.app.data.model.savemodule


import com.google.gson.annotations.SerializedName

data class DataSaveModule(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("modul_id")
    val modulId: String,
    @SerializedName("semester")
    val semester: Int,
    @SerializedName("siswa_id")
    val siswaId: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)