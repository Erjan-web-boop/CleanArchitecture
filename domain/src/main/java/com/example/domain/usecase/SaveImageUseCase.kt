package com.example.domain.usecase

import com.example.domain.model.TaskModel
import com.example.domain.repository.TaskManagerRepository

class SaveImageUseCase(private val taskManagerRepository: TaskManagerRepository) {

    suspend operator fun invoke(taskModel: TaskModel, imageUri: String) {
        val updatedTask = taskModel.copy(imageUri = imageUri)
        taskManagerRepository.saveImage(updatedTask)
    }
}