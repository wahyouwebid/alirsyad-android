package com.alirsyad.app.data.model.simulation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Simulation(
    val name: String,
    val simulationProgress: String,
    val progress: Int
) : Parcelable
