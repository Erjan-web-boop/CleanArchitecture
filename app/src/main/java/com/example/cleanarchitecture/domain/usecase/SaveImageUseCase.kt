package com.example.cleanarchitecture.domain.usecase

import com.example.cleanarchitecture.domain.model.TaskModel
import com.example.cleanarchitecture.domain.repository.TaskManagerRepository

class SaveImageUseCase(private val taskManagerRepository: TaskManagerRepository) {

    suspend operator fun invoke(taskModel: TaskModel, imageUri: String) {
        val updatedTask = taskModel.copy(imageUri = imageUri)
        taskManagerRepository.saveImage(updatedTask)
    }
}