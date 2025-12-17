package com.alirsyad.app.data.model.intro

import android.graphics.drawable.Drawable
import com.google.gson.annotations.SerializedName

data class DataIntro(
    @SerializedName("title") var title: String = "",
    @SerializedName("description") var description: String = "",
    @SerializedName("image") var image: Drawable?
)