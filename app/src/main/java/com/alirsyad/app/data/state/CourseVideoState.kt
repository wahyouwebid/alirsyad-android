package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.video.ResponseVideo

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class CourseVideoState {
    object Loading : CourseVideoState()
    data class Result(val data: ResponseVideo) : CourseVideoState()
    data class Error(val error: Throwable) : CourseVideoState()
}
