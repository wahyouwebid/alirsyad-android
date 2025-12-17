package com.alirsyad.app.data.model.achievement


import com.google.gson.annotations.SerializedName

data class Achievement(
    @SerializedName("mata_pelajaran")
    val mataPelajaran: String?,
    @SerializedName("progress")
    val progress: Progress?,
    @SerializedName("simulasis")
    val simulasis: List<Simulasi>
)