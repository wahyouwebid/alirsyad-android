package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.ResponseArrayObject
import com.alirsyad.app.data.model.ereport.EReportScore

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class EReportScoreState {
    object Loading : EReportScoreState()
    data class Result(val data: ResponseArrayObject<EReportScore>) : EReportScoreState()
    data class Error(val error: Throwable) : EReportScoreState()
}