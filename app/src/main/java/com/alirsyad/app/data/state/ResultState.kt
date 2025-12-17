package com.alirsyad.app.data.state

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class ResultState<T> {
    object Loading : ResultState<Nothing>()
    data class Result<T>(val data: T): ResultState<T>()
    data class Error(val error: Throwable) : ResultState<Nothing>()
}