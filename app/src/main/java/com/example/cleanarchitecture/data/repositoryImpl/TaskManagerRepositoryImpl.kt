package com.example.cleanarchitecture.data.repositoryImpl

import com.example.cleanarchitecture.data.database.dao.TaskManagerDao
import com.example.cleanarchitecture.data.dto.toData
import com.example.cleanarchitecture.data.dto.toDomain
import com.example.cleanarchitecture.domain.model.TaskModel
import com.example.cleanarchitecture.domain.repository.TaskManagerRepository

class TaskManagerRepositoryImpl(private val taskManagerDao: TaskManagerDao): TaskManagerRepository {

    override suspend fun insertTask(taskModel: TaskModel) {
        taskManagerDao.insertTask(taskModel.toData())
    }

    override suspend fun getAllTask(): List<TaskModel> {
        return taskManagerDao.getAllTasks().map { it.toDomain() }
    }

    override suspend fun getTaskByName(taskName: String): TaskModel? {
        return taskManagerDao.getTaskByName(taskName)?.toDomain()
    }

}