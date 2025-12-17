package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.ResponseObject
import com.alirsyad.app.data.model.ereport.EReportAchievement

sealed class AchievementState {
    object Loading : AchievementState()
    data class Result(val data: ResponseObject<EReportAchievement>) : AchievementState()
    data class Error(val error: Throwable) : AchievementState()
}