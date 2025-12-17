package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.user.ResponseLogin

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class LoginState {
    object Loading : LoginState()
    data class Result(val data: ResponseLogin) : LoginState()
    data class Error(val error: Throwable) : LoginState()
}
