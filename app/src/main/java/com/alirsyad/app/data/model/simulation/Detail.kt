package com.alirsyad.app.data.model.simulation


import com.google.gson.annotations.SerializedName

data class Detail(
    @SerializedName("mata_pelajaran")
    val mataPelajaran: String,
    @SerializedName("progress")
    val progress: Progress,
    @SerializedName("simulasis")
    val simulasis: List<Simulasi>
)