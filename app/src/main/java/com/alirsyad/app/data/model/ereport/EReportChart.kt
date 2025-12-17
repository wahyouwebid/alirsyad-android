package com.alirsyad.app.data.model.ereport


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class EReportChart(
    @SerializedName("mata_pelajaran")
    val mataPelajaran: String?,
    @SerializedName("mudah")
    val mudah: Mudah?,
    @SerializedName("sedang")
    val sedang: Sedang?,
    @SerializedName("sulit")
    val sulit: Sulit?,
)

@Parcelize
data class ChartLegend(
    val color: String,
    val label: String?,
) : Parcelable
