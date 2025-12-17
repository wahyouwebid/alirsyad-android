package com.alirsyad.app.ui.subjectsummary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alirsyad.app.data.repository.Repository
import com.alirsyad.app.data.state.SummaryOfLevelState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubjectSummaryViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val summaryOfLevel: MutableLiveData<SummaryOfLevelState> by lazy {
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

    fun getSummaryOfLevel(courseId : Int){
        repository.getSummaryOfLevel(
            repository.getToken(),
            courseId,
            repository.getUserId(),
            summaryOfLevel
        )
    }

    fun getIsPengunjung(): Int {
        return repository.getIsPengunjung()
    }
}