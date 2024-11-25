package com.example.cleanarchitecture.presentation.addtask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecture.presentation.base.BaseViewModel
import com.example.cleanarchitecture.presentation.uimodule.TaskUI
import com.example.cleanarchitecture.presentation.uimodule.toDomain
import com.example.domain.usecase.InsertTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddTaskViewModel(
    private val insertTaskUseCase: InsertTaskUseCase,
) : BaseViewModel() {

    private val _insertMessageStateFlow = MutableStateFlow(String())
    val insertMessageFlow: StateFlow<String> = _insertMessageStateFlow.asStateFlow()

    private val _isTaskInserted = MutableLiveData<Boolean>()
    val isTaskInserted: LiveData<Boolean> get() = _isTaskInserted

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

}