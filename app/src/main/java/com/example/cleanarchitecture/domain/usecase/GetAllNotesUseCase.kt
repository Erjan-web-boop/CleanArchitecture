package com.example.cleanarchitecture.domain.usecase

import com.example.cleanarchitecture.domain.model.TaskModel
import com.example.cleanarchitecture.domain.repository.TaskManagerRepository

class GetAllNotesUseCase(
    private val taskManagerRepository: TaskManagerRepository
) {

    suspend operator fun invoke(): List<TaskModel>{
        return taskManagerRepository.getAllTask()
    }
}