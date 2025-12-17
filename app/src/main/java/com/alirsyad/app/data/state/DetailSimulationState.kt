package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.detailsimulation.ResponseDetailSimulation

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class DetailSimulationState {
    object Loading : DetailSimulationState()
    data class Result(val data: ResponseDetailSimulation) : DetailSimulationState()
    data class Error(val error: Throwable) : DetailSimulationState()
}
