package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.savemodule.ResponseSaveModule

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class SaveModuleState {
    object Loading : SaveModuleState()
    data class Result(val data: ResponseSaveModule) : SaveModuleState()
    data class Error(val error: Throwable) : SaveModuleState()
}
