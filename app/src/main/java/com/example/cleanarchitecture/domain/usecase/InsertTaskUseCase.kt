package com.example.cleanarchitecture.domain.usecase

import com.example.cleanarchitecture.domain.model.TaskModel
import com.example.cleanarchitecture.domain.repository.TaskManagerRepository
import java.time.LocalDateTime

class InsertTaskUseCase(private val taskManagerRepository: TaskManagerRepository) {

    suspend fun insertTask(taskModel: TaskModel): String {
        val existingTask = taskManagerRepository.getTaskByName(taskModel.taskName)
        if (existingTask != null) {
            return "This already exists"
        }
        val taskDate = taskModel.taskDate.toIntOrNull()
        val currentHour = LocalDateTime.now().hour

        if (taskDate == null || taskDate < currentHour) {
            return "trying to add a task for a while"
        }
        taskManagerRepository.insertTask(taskModel)
        return "Task added successfully"
    }
}
