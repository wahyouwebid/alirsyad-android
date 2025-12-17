package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.ResponseArrayObject
import com.alirsyad.app.data.model.ereport.EReportChart

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class EReportChartState {
    object Loading : EReportChartState()
    data class Result(val data: ResponseArrayObject<EReportChart>) : EReportChartState()
    data class Error(val error: Throwable) : EReportChartState()
}
