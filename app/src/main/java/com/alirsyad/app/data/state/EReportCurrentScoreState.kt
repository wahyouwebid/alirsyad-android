package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.ResponseArrayObject
import com.alirsyad.app.data.model.ereport.EReportCurrentScore

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class EReportCurrentScoreState {
    object Loading : EReportCurrentScoreState()
    data class Result(val data: ResponseArrayObject<EReportCurrentScore>) : EReportCurrentScoreState()
    data class Error(val error: Throwable) : EReportCurrentScoreState()
}