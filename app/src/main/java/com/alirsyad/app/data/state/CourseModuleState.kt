package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.module.ResponseModule

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class CourseModuleState {
    object Loading : CourseModuleState()
    data class Result(val data: ResponseModule) : CourseModuleState()
    data class Error(val error: Throwable) : CourseModuleState()
}
