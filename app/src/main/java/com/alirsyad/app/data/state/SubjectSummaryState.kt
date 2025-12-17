package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.ResponseArrayObject
import com.alirsyad.app.data.model.summaryoflevel.SummaryOfLevel

sealed class SubjectSummaryState {
    object Loading : SubjectSummaryState()
    data class Result(val data: ResponseArrayObject<SummaryOfLevel>) : SubjectSummaryState()
    data class Error(val error: Throwable) : SubjectSummaryState()
}