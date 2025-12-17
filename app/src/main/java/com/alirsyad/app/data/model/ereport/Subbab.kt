package com.alirsyad.app.data.model.ereport


import com.google.gson.annotations.SerializedName

data class Subbab(
    @SerializedName("nama")
    val nama: String?,
    @SerializedName("nilai")
    val nilai: List<Nilai>
)