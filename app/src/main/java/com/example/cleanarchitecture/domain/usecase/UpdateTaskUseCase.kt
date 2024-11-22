package com.example.cleanarchitecture.domain.usecase

import com.example.cleanarchitecture.domain.model.TaskModel
import com.example.cleanarchitecture.domain.repository.TaskManagerRepository

class UpdateTaskUseCase(private val taskManagerRepository: TaskManagerRepository) {
    suspend operator fun invoke(taskModel: TaskModel) {
        taskManagerRepository.insertTask(taskModel)
    }
}