package com.alirsyad.app.ui.updatesubject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alirsyad.app.data.repository.Repository
import com.alirsyad.app.data.state.UpdateState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateSubjectViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val newUpdate: MutableLiveData<UpdateState> by lazy {
        MutableLiveData()
    }

    fun getNewUpdate() {
        repository.getUpdate(repository.getToken(), newUpdate)
    }
}