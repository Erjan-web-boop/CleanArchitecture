package com.example.domain.usecase

import com.example.domain.model.TaskModel
import com.example.domain.repository.TaskManagerRepository

class DeleteTaskUseCase(private val taskManagerRepository: TaskManagerRepository) {

    suspend operator fun invoke(taskModel: TaskModel) {
        taskManagerRepository.delete(taskModel)
    }
}
