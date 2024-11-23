package com.example.cleanarchitecture.presentation.activity


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecture.domain.usecase.DeleteTaskUseCase
import com.example.cleanarchitecture.domain.usecase.GetAllNotesUseCase
import com.example.cleanarchitecture.domain.usecase.GetTaskUseCase
import com.example.cleanarchitecture.domain.usecase.InsertTaskUseCase
import com.example.cleanarchitecture.domain.usecase.SaveImageUseCase
import com.example.cleanarchitecture.domain.usecase.UpdateTaskUseCase
import com.example.cleanarchitecture.presentation.base.BaseViewModel
import com.example.cleanarchitecture.presentation.uimodule.TaskUI
import com.example.cleanarchitecture.presentation.uimodule.toDomain
import com.example.cleanarchitecture.presentation.uimodule.toUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeoutException

class ActivityViewModel(
    private val insertTaskUseCase: InsertTaskUseCase,
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val getTaskUseCase: GetTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val saveImageUseCase: SaveImageUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) : BaseViewModel() {

    private val _tasksStateFlow = MutableStateFlow<List<TaskUI>>(emptyList())
    val tasksFlow: StateFlow<List<TaskUI>> = _tasksStateFlow.asStateFlow()

    private val _insertMessageStateFlow = MutableStateFlow(String())
    val insertMessageFlow: StateFlow<String> = _insertMessageStateFlow.asStateFlow()

    private val _isTaskInserted = MutableLiveData<Boolean>()
    val isTaskInserted: LiveData<Boolean> get() = _isTaskInserted

    fun saveImage(taskUI: TaskUI, imageUri: String) {
        viewModelScope.launch(Dispatchers.IO) {
            saveImageUseCase(taskUI.toDomain(), imageUri)
        }
    }

    fun insertTask(taskUI: TaskUI) {
        viewModelScope.runLaunchIO(
            block = {
                val message = insertTaskUseCase.insertTask(taskUI.toDomain())
                _insertMessageStateFlow.value = message
                _isTaskInserted.postValue(true)
            },
            onError = {error ->
                _errorState.value = "Ошибка при вставке задачи: ${error.message}"
            }
        )
    }

    suspend fun updateTask(taskUI: TaskUI) {
        withContext(Dispatchers.IO) {
            updateTaskUseCase(taskUI.toDomain())
        }
    }

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

    suspend fun getTask(id: Int): TaskUI {
        return getTaskUseCase(id).toUi()
    }

}