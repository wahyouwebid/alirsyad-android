package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.forgot.BaseResponseArray

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class CommonState {
    object Loading : CommonState()
    data class Result(val data: BaseResponseArray) : CommonState()
    data class Error(val error: Throwable) : CommonState()
}
