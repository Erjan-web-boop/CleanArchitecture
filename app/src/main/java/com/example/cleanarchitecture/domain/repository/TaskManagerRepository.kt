package com.example.cleanarchitecture.domain.repository


import com.example.cleanarchitecture.domain.model.TaskModel

interface TaskManagerRepository {

    suspend fun insertTask(taskModel: TaskModel)
    suspend fun getAllTask(): List<TaskModel>
    suspend fun getTaskByName(taskName: String): TaskModel ?
}