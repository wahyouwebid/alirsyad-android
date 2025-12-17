package com.alirsyad.app.data.model.adaptive


import com.google.gson.annotations.SerializedName

data class AdaptiveModule(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("paket_soal")
    val paketSoal: List<QuestionPackage>
)