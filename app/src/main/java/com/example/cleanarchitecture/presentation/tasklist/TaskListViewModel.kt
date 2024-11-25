package com.example.cleanarchitecture.presentation.tasklist

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecture.presentation.base.BaseViewModel
import com.example.cleanarchitecture.presentation.uimodule.TaskUI
import com.example.cleanarchitecture.presentation.uimodule.toDomain
import com.example.cleanarchitecture.presentation.uimodule.toUi
import com.example.domain.usecase.DeleteTaskUseCase
import com.example.domain.usecase.GetAllNotesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getAllNotesUseCase: GetAllNotesUseCase
):  BaseViewModel() {

    private val _tasksStateFlow = MutableStateFlow<List<TaskUI>>(emptyList())
    val tasksFlow: StateFlow<List<TaskUI>> = _tasksStateFlow.asStateFlow()

    fun loadTasks() {
        viewModelScope.runLaunchIO(
            block = {
                getAllNotesUseCase().onEach {
                    _tasksStateFlow.value = it.map { model -> model.toUi() }
                }.collect()
            },
            onError = {
                    exception ->
                Log.e("LoadTasks", "Ошибка загрузки задач", exception)
            }
        )
    }

    fun deleteTask(taskUI: TaskUI) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTaskUseCase(taskUI.toDomain())
            loadTasks()
        }
    }
}