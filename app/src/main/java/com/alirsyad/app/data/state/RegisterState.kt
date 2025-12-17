package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.register.ResponseRegister

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class RegisterState {
    object Loading : RegisterState()
    data class Result(val data: ResponseRegister) : RegisterState()
    data class Error(val error: Throwable) : RegisterState()
}
