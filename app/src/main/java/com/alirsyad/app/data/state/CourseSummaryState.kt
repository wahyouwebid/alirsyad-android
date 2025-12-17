package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.summary.ResponseCourseSummary

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class CourseSummaryState {
    object Loading : CourseSummaryState()
    data class Result(val data: ResponseCourseSummary) : CourseSummaryState()
    data class Error(val error: Throwable) : CourseSummaryState()
}
