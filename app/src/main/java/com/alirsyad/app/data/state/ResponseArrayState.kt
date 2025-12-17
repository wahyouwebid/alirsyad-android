package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.ResponseArrayObject
import com.alirsyad.app.data.model.adaptive.AdaptiveModule

sealed class ResponseArrayState {
    object Loading : ResponseArrayState()
    data class Result(val data: ResponseArrayObject<AdaptiveModule>) : ResponseArrayState()
    data class Error(val error: Throwable) : ResponseArrayState()
}