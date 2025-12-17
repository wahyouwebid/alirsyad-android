package com.alirsyad.app.data.model.params

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataParams(
    val id : Int?,
    var name : String?,
    var tingkat : String?,
    var jenjang : String?
) : Parcelable