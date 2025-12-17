package com.alirsyad.app.data.model.previewadaptive


import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class DataPreviewAdaptive(
    @SerializedName("list_soal")
    @Expose
    val listSoal: List<PreviewAdaptiveSoal>,
    @SerializedName("paket_soal_id")
    @Expose
    val paketSoalId: Int?,
    @SerializedName("tingkat_kesulitan")
    @Expose
    val tingkatKesulitan: String?
)