package com.alirsyad.app.data.model.simulation


import com.google.gson.annotations.SerializedName

data class DataSimulation(
    @SerializedName("jenjang")
    val jenjang: String,
    @SerializedName("kelas")
    val kelas: String,
    @SerializedName("mata_pelajarans")
    val mataPelajarans: List<MataPelajaran>,
    @SerializedName("tingkat")
    val tingkat: String
)