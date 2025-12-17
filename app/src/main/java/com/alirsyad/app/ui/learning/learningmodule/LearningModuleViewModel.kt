package com.alirsyad.app.ui.learning.learningmodule

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alirsyad.app.data.repository.Repository
import com.alirsyad.app.data.state.CourseModuleState
import com.alirsyad.app.data.state.DetailCourseState
import com.alirsyad.app.data.state.DetailModuleState
import com.alirsyad.app.data.state.SaveModuleState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LearningModuleViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val module: MutableLiveData<CourseModuleState> by lazy {
        MutableLiveData()
    }

    val detailModule: MutableLiveData<DetailModuleState> by lazy {
        MutableLiveData()
    }

    val saveModule: MutableLiveData<SaveModuleState> by lazy {
        MutableLiveData()
    }

    val courseDetail: MutableLiveData<DetailCourseState> by lazy {
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

    fun getStatus(): String {
        return repository.getStatus()
    }

    fun getData(id: Int) {
        repository.getCourseModule(repository.getToken(), id, module)
    }

    fun getDetailModule(id: Int){
        repository.getDetailModule(repository.getToken(), id, detailModule)
    }

    fun saveFlag(id: Int) {
        repository.saveFlagModule(repository.getToken(), id, saveModule)
    }

    fun getCourseDetail(id: Int) {
        repository.getCourseDetail(repository.getToken(), id, courseDetail)
    }
}