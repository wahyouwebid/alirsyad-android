package com.alirsyad.app.data.model.detailmodule


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Next(
    @SerializedName("endpoint")
    val endpoint: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("slug_url")
    val slugUrl: String?,
    @SerializedName("url")
    val url: String?
): Parcelable