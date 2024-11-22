package com.example.cleanarchitecture.domain.usecase

import com.example.cleanarchitecture.domain.model.TaskModel
import com.example.cleanarchitecture.domain.repository.TaskManagerRepository
import kotlinx.coroutines.flow.Flow

class GetAllNotesUseCase(
    private val taskManagerRepository: TaskManagerRepository
) {

    suspend operator fun invoke(): Flow<List<TaskModel>> {
        return taskManagerRepository.getAllTask()
    }
}