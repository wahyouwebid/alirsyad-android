package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.ResponseObject
import com.alirsyad.app.data.model.achievement.Achievement

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class AchievementSimulationState {
    object Loading : AchievementSimulationState()
    data class Result(val data: ResponseObject<Achievement>) : AchievementSimulationState()
    data class Error(val error: Throwable) : AchievementSimulationState()
}
