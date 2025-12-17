package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.ResponseObject
import com.alirsyad.app.data.model.ereport.EReportAchievement

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class EReportAchievementState {
    object Loading : EReportAchievementState()
    data class Result(val data: ResponseObject<EReportAchievement>) : EReportAchievementState()
    data class Error(val error: Throwable) : EReportAchievementState()
}
