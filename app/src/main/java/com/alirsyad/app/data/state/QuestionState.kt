package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.ResponseObject
import com.alirsyad.app.data.model.adaptive.DataQuestion

sealed class QuestionState {
    object Loading : QuestionState()
    data class Result(val data: ResponseObject<DataQuestion>) : QuestionState()
    data class Error(val error: Throwable) : QuestionState()
}