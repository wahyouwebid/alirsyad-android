package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.ResponseArrayObject
import com.alirsyad.app.data.model.summaryoflevel.SummaryOfLevel

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class SummaryOfLevelState {
    object Loading : SummaryOfLevelState()
    data class Result(val data: ResponseArrayObject<SummaryOfLevel>) : SummaryOfLevelState()
    data class Error(val error: Throwable) : SummaryOfLevelState()
}
