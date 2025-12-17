package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.simulation.ResponseSimulation

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class SimulationState {
    object Loading : SimulationState()
    data class Result(val data: ResponseSimulation) : SimulationState()
    data class Error(val error: Throwable) : SimulationState()
}
