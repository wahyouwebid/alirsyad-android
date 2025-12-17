package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.profile.upload.ResponseUploadPhoto

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class UploadPhotoState {
    object Loading : UploadPhotoState()
    data class Result(val data: ResponseUploadPhoto) : UploadPhotoState()
    data class Error(val error: Throwable) : UploadPhotoState()
}
