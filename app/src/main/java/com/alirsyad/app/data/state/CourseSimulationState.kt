package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.simulation.ResponseLearningSimulation

/**
 * Created by : wahyouwebid.
 * Email : hello@wahyou.web.id.
 * Linkedin : linkedin.com/in/wahyouwebid.
 * Instagram : instagram.com/wahyouwebid.
 * Portopolio : wahyou.web.id.
 */

sealed class CourseSimulationState {
    object Loading : CourseSimulationState()
    data class Result(val data: ResponseLearningSimulation) : CourseSimulationState()
    data class Error(val error: Throwable) : CourseSimulationState()
}
