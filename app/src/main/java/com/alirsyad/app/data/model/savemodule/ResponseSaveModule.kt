package com.alirsyad.app.data.model.savemodule


import com.google.gson.annotations.SerializedName

data class ResponseSaveModule(
    @SerializedName("data")
    val data: com.alirsyad.app.data.model.savemodule.DataSaveModule,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)