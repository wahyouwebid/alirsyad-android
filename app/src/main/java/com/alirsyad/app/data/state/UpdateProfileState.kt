package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.profile.update.ResponseUpdateProfile

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class UpdateProfileState {
    object Loading : UpdateProfileState()
    data class Result(val data: ResponseUpdateProfile) : UpdateProfileState()
    data class Error(val error: Throwable) : UpdateProfileState()
}
