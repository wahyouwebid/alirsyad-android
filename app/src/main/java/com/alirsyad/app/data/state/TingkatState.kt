package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.tingkat.ResponseTingkat

sealed class TingkatState {
    object Loading : TingkatState()
    data class Result(val data: ResponseTingkat) : TingkatState()
    data class Error(val error: Throwable) : TingkatState()
}