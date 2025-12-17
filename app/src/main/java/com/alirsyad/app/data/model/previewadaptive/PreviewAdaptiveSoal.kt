package com.alirsyad.app.data.model.previewadaptive


import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class PreviewAdaptiveSoal(
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("image")
    @Expose
    val image: String?,
    @SerializedName("is_correct")
    @Expose
    val isCorrect: Boolean?,
    @SerializedName("jawaban")
    @Expose
    val jawaban: List<String?>?,
    @SerializedName("jawaban_benar")
    @Expose
    val jawabanBenar: String?,
    @SerializedName("jawaban_siswa")
    @Expose
    val jawabanSiswa: String?,
    @SerializedName("pembahasan_text")
    @Expose
    val pembahasanText: String?,
    @SerializedName("pembahasan_video")
    @Expose
    val pembahasanVideo: String?,
    @SerializedName("soal")
    @Expose
    val soal: String?
)