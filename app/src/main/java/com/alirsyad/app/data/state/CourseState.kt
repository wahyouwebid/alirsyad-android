package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.courses.ResponseCourses

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class CourseState {
    object Loading : CourseState()
    data class Result(val data: ResponseCourses) : CourseState()
    data class Error(val error: Throwable) : CourseState()
}
