package com.alirsyad.app.data.model.summary


import com.google.gson.annotations.SerializedName

data class DataSummary(
    @SerializedName("progress_modul")
    val progressModul: ProgressModul,
    @SerializedName("progress_simulasi")
    val progressSimulasi: ProgressSimulasi,
    @SerializedName("progress_video")
    val progressVideo: ProgressVideo
)