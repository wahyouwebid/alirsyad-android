package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.courses.ResponseCourseDetail

sealed class DetailCourseState {
    object Loading : DetailCourseState()
    data class Result(val data: ResponseCourseDetail) : DetailCourseState()
    data class Error(val error: Throwable) : DetailCourseState()
}
