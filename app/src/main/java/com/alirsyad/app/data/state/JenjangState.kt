package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.jenjang.ResponseJenjang

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class JenjangState {
    object Loading : JenjangState()
    data class Result(val data: ResponseJenjang) : JenjangState()
    data class Error(val error: Throwable) : JenjangState()
}
