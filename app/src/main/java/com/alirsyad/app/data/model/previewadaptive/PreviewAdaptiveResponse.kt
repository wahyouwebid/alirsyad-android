package com.alirsyad.app.data.model.previewadaptive


import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class PreviewAdaptiveResponse(
    @SerializedName("data")
    @Expose
    val data: DataPreviewAdaptive?,
    @SerializedName("success")
    @Expose
    val success: Boolean?
)