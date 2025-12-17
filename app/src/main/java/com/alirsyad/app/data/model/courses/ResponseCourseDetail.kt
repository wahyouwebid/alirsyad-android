package com.alirsyad.app.data.model.courses

import com.google.gson.annotations.SerializedName

data class ResponseCourseDetail(
    @SerializedName("data")
    val data: com.alirsyad.app.data.model.courses.DataCourse,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)