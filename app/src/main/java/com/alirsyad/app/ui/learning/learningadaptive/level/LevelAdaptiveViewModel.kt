package com.alirsyad.app.ui.learning.learningadaptive.level

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alirsyad.app.data.repository.Repository
import com.alirsyad.app.data.state.LevelState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LevelAdaptiveViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val level: MutableLiveData<LevelState> by lazy {
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

    fun getIsPengunjung(): Int {
        return repository.getIsPengunjung()
    }

    fun getToken(): String {
        return repository.getToken()
    }

    fun getStatus(): String {
        return repository.getStatus()
    }

    fun getUserId(): Int {
        return repository.getUserId()
    }

    fun getData(paketSoalId: Int) {
        repository.getLevel(getToken(), paketSoalId, getUserId(), level)
    }
}