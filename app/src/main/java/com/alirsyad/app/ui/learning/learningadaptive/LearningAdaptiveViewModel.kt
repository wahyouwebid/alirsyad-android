package com.alirsyad.app.ui.learning.learningadaptive

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alirsyad.app.data.repository.Repository
import com.alirsyad.app.data.state.ResponseArrayState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LearningAdaptiveViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val questionPackage: MutableLiveData<ResponseArrayState> by lazy {
        MutableLiveData()
    }

    val clearQuestion: MutableLiveData<Int> by lazy {
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

    fun getData(id: Int) {
        repository.getQuestionPackage(getToken(), id, questionPackage)
    }

    fun clearQuestion() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.clearQuestion(clearQuestion)
            withContext(Dispatchers.Main){
                clearQuestion.postValue(1)
            }
        }
    }
}