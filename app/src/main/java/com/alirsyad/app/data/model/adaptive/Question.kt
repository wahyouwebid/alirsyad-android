package com.alirsyad.app.data.model.adaptive


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    @SerializedName("id")
    var id: Int,
    @SerializedName("image")
    val image: String?,
    @SerializedName("jawaban")
    val jawaban: List<String>?,
    @SerializedName("soal")
    val soal: String?,
    var idAnswer: Int,
    var answer: String?,
    var isAnswered: Int? = null,
): Parcelable