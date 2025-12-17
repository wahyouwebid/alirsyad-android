package com.alirsyad.app.ui.learning.learningsimulation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alirsyad.app.data.repository.Repository
import com.alirsyad.app.data.state.CourseSimulationState
import com.alirsyad.app.data.state.DetailSimulationState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LearningSimulationViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val simulation: MutableLiveData<CourseSimulationState> by lazy {
        MutableLiveData()
    }

    val detailSimulation: MutableLiveData<DetailSimulationState> by lazy {
        MutableLiveData()
    }

    fun getName(): String {
        return repository.getName()
    }

    fun getKelas(): String {
        return repository.getKelas()
    }

    fun getTingkat(): String {
        return repository.getSubjectClass()
    }

    fun getToken(): String {
        return repository.getToken()
    }

    fun getIsPengunjung(): Int {
        return repository.getIsPengunjung()
    }

    fun getStatus(): String {
        return repository.getStatus()
    }

    fun getData(id: Int) {
        repository.getCourseSimulation(repository.getToken(), id, simulation)
    }

    fun getDetailSimulation(id: Int) {
        repository.getDetailSimulation(repository.getToken(), id, detailSimulation)
    }

}