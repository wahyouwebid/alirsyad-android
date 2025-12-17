package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.ResponseArrayObject
import com.alirsyad.app.data.model.level.Level

sealed class LevelState {
    object Loading : LevelState()
    data class Result(val data: ResponseArrayObject<Level>) : LevelState()
    data class Error(val error: Throwable) : LevelState()
}