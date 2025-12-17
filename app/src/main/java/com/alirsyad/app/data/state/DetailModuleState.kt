package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.detailmodule.ResponseDetailModule

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class DetailModuleState {
    object Loading : DetailModuleState()
    data class Result(val data: ResponseDetailModule) : DetailModuleState()
    data class Error(val error: Throwable) : DetailModuleState()
}
