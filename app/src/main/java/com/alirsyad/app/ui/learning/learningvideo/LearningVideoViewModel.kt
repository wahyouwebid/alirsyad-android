package com.alirsyad.app.ui.learning.learningvideo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alirsyad.app.data.repository.Repository
import com.alirsyad.app.data.state.CourseVideoState
import com.alirsyad.app.data.state.DetailCourseState
import com.alirsyad.app.data.state.DetailVideoState
import com.alirsyad.app.data.state.SaveVideoState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LearningVideoViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val video: MutableLiveData<CourseVideoState> by lazy {
        MutableLiveData()
    }

    val detailVideo: MutableLiveData<DetailVideoState> by lazy {
        MutableLiveData()
    }

    val saveVideo: MutableLiveData<SaveVideoState> by lazy {
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
        repository.getCourseVideo(repository.getToken(), id, video)
    }

    fun getDetailVideon(id: Int){
        repository.getDetailVideo(repository.getToken(), id, detailVideo)
    }

    fun saveFlag(id: Int){
        repository.saveFlagVideo(repository.getToken(), id, saveVideo)
    }

    fun getCourseDetail(id: Int) {
        repository.getCourseDetail(repository.getToken(), id, courseDetail)
    }
}