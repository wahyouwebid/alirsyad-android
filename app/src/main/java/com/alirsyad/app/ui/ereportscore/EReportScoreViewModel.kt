package com.alirsyad.app.ui.ereportscore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alirsyad.app.data.repository.Repository
import com.alirsyad.app.data.state.CourseState
import com.alirsyad.app.data.state.EReportScoreState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EReportScoreViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val course: MutableLiveData<CourseState> by lazy {
        MutableLiveData()
    }

    val module: MutableLiveData<EReportScoreState> by lazy {
        MutableLiveData()
    }

    fun getCourse() {
        repository.getMapelByClass(repository.getToken(), repository.getTingkatId(), course)
    }

    fun getData(courseId: Int) {
        repository.getEReportYourScore(
            repository.getToken(),
            repository.getUserId(),
            courseId,
            module
        )
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

}