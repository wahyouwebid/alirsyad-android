package com.alirsyad.app.data.model.detailmodule


import com.google.gson.annotations.SerializedName

data class ResponseDetailModule(
    @SerializedName("data")
    val data: com.alirsyad.app.data.model.detailmodule.DataModule,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)