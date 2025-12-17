package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.ResponseObject
import com.alirsyad.app.data.model.adaptive.CheckAnswer

sealed class CheckAnswerState {
    object Loading : CheckAnswerState()
    data class Result(val data: ResponseObject<CheckAnswer>) : CheckAnswerState()
    data class Error(val error: Throwable) : CheckAnswerState()
}