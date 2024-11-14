package com.example.cleanarchitecture.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecture.domain.usecase.GetAllNotesUseCase
import com.example.cleanarchitecture.domain.usecase.InsertTaskUseCase
import com.example.cleanarchitecture.presentation.uimodule.TaskUI
import com.example.cleanarchitecture.presentation.uimodule.toDomain
import com.example.cleanarchitecture.presentation.uimodule.toUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityViewModel (
    private val insertTaskUseCase: InsertTaskUseCase,
    private val getAllNotesUseCase: GetAllNotesUseCase
) : ViewModel() {

    private val _tasks = MutableLiveData<List<TaskUI>>()
    val tasks: LiveData<List<TaskUI>> get() = _tasks

    private val _insertMessage = MutableLiveData<String>()
    val insertMessage: LiveData<String> get() = _insertMessage

    fun insertTask(taskUI: TaskUI) {
        viewModelScope.launch(Dispatchers.IO) {
            val message = insertTaskUseCase.insertTask(taskUI.toDomain())
            _insertMessage.postValue(message)
        }
    }

    fun loadTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val allTasks = getAllNotesUseCase()
            _tasks.postValue(allTasks.map { it.toUi() })
        }
    }
}