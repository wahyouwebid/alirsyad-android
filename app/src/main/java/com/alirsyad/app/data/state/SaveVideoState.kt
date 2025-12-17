package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.savevideo.ResponseSaveVideo

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class SaveVideoState {
    object Loading : SaveVideoState()
    data class Result(val data: ResponseSaveVideo) : SaveVideoState()
    data class Error(val error: Throwable) : SaveVideoState()
}
