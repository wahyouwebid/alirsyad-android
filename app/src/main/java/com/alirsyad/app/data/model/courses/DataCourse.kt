package com.alirsyad.app.data.model.courses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataCourse(
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("deleted_at")
    val deletedAt: String? = "",
    @SerializedName("disabled")
    val disabled: Boolean = false,
    @SerializedName("icon")
    val icon: String = "",
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("urutan")
    val urutan: Int = -1,
    @SerializedName("tingkat_id")
    val tingkatId: Int = -1,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("slug")
    val slug: String = "",
    @SerializedName("updated_at")
    val updatedAt: String? = "",
    @SerializedName("disable")
    val disable: Boolean? = false,
    @SerializedName("tingkat")
    val tingkat: Tingkat? = null,

    ) : Parcelable