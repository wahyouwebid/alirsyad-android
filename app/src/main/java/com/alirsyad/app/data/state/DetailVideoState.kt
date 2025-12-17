package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.detailvideo.ResponseDetailVideo

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class DetailVideoState {
    object Loading : DetailVideoState()
    data class Result(val data: ResponseDetailVideo) : DetailVideoState()
    data class Error(val error: Throwable) : DetailVideoState()
}
