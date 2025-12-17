package com.alirsyad.app.data.state

sealed class ResponseState {
    object Loading : ResponseState()
    data class Result<T>(val data: T) : ResponseState()
    data class Error(val error: Throwable) : ResponseState()
}