package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.update.ResponseUpdate

sealed class UpdateState {
    object Loading : UpdateState()
    data class Result(val data: ResponseUpdate) : UpdateState()
    data class Error(val error: Throwable) : UpdateState()
}