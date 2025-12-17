package com.alirsyad.app.data.model.courses

import com.google.gson.annotations.SerializedName

data class ResponseCourses(
    @SerializedName("data")
    val data: List<com.alirsyad.app.data.model.courses.DataCourse>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)