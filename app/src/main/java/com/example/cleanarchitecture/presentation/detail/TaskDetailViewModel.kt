package com.example.cleanarchitecture.presentation.detail


import com.example.cleanarchitecture.presentation.base.BaseViewModel
import com.example.cleanarchitecture.presentation.uimodule.TaskUI
import com.example.cleanarchitecture.presentation.uimodule.toDomain
import com.example.cleanarchitecture.presentation.uimodule.toUi
import com.example.domain.usecase.GetTaskUseCase
import com.example.domain.usecase.UpdateTaskUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskDetailViewModel(
    private val getTaskUseCase: GetTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
): BaseViewModel() {

    suspend fun updateTask(taskUI: TaskUI) {
        withContext(Dispatchers.IO) {
            updateTaskUseCase(taskUI.toDomain())
        }
    }


    suspend fun getTask(id: Int): TaskUI {
        return getTaskUseCase(id).toUi()
    }

}