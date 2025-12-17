package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.profile.ResponseProfile

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class ProfileState {
    object Loading : ProfileState()
    data class Result(val data: ResponseProfile) : ProfileState()
    data class Error(val error: Throwable) : ProfileState()
}
