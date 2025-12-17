package com.alirsyad.app.ui.learning.learningadaptive.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alirsyad.app.data.entity.QuestionEntity
import com.alirsyad.app.data.mapper.Mapper
import com.alirsyad.app.data.model.adaptive.CheckAnswerRequest
import com.alirsyad.app.data.model.adaptive.PreviewRequest
import com.alirsyad.app.data.model.adaptive.Question
import com.alirsyad.app.data.repository.Repository
import com.alirsyad.app.data.state.CheckAnswerState
import com.alirsyad.app.data.state.PreviewAdaptiveState
import com.alirsyad.app.data.state.QuestionState
import com.google.gson.Gson
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailLearningAdaptiveViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val question: MutableLiveData<QuestionState> by lazy {
        MutableLiveData()
    }

    val checkAnswer: MutableLiveData<CheckAnswerState> by lazy {
        MutableLiveData()
    }

    val getQuestion: MutableLiveData<List<QuestionEntity>> by lazy {
        MutableLiveData()
    }

    val insertQuestion: MutableLiveData<Int> by lazy {
        MutableLiveData()
    }

    val updateQuestion: MutableLiveData<Int> by lazy {
        MutableLiveData()
    }

    val preview: MutableLiveData<PreviewAdaptiveState> by lazy {
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

    fun getUserId(): Int {
        return repository.getUserId()
    }

    fun getData(id: Int?) {
        if (id != null) {
            repository.getQuestion(getToken(), id, question)
        }
    }

    fun checkAnswer(request: CheckAnswerRequest) {
        val body = JsonParser.parseString(Gson().toJson(request)).asJsonObject
        repository.checkAnswer(getToken(), body, checkAnswer)
    }

    fun getQuestion() {
        repository.getQuestion(getQuestion)
    }

    fun getPreview(request: PreviewRequest?) {
        val body = JsonParser.parseString(Gson().toJson(request)).asJsonObject
        repository.getPreviewAdaptive(getToken(), body, preview)
    }

    fun insertQuestion(data: List<Question>) {
        val dataQuestion = Mapper.mapResponsesToEntities(data)
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertQuestion(dataQuestion, insertQuestion)
            withContext(Dispatchers.Main){
                insertQuestion.postValue(1)
            }
        }
    }

    fun updateQuestion(data: QuestionEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateQuestion(data, updateQuestion)
            withContext(Dispatchers.Main){
                updateQuestion.postValue(1)
            }
        }
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