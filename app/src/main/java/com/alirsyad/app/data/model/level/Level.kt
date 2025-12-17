package com.alirsyad.app.data.model.level

import com.google.gson.annotations.SerializedName

data class Level(
    @SerializedName("paket_soal_id")
    val paketSoalId: Int,

    @SerializedName("tingkat_kesulitan")
    val tingkatKesulitan: String,

    @SerializedName("is_available")
    val isAvailable: Boolean,

)
