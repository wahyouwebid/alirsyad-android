package com.alirsyad.app.data.model.adaptive

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnswerSelected (
    @SerializedName("id")
    var id: Int,
    @SerializedName("jawaban")
    var jawaban: String?,

): Parcelable