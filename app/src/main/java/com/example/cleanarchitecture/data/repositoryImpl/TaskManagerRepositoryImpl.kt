package com.example.cleanarchitecture.data.repositoryImpl



import com.example.cleanarchitecture.data.database.dao.TaskManagerDao
import com.example.cleanarchitecture.data.dto.toData
import com.example.cleanarchitecture.data.dto.toDomain
import com.example.cleanarchitecture.domain.model.TaskModel
import com.example.cleanarchitecture.domain.repository.TaskManagerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskManagerRepositoryImpl(private val taskManagerDao: TaskManagerDao): TaskManagerRepository {

    override suspend fun insertTask(taskModel: TaskModel) {
        taskManagerDao.insertTask(taskModel.toData())
    }

    override suspend fun getAllTask(): Flow<List<TaskModel>> {
        return taskManagerDao.getAllTasks().map { list ->
            list.map { dto ->
                dto.toDomain()
            }
        }
    }

    override suspend fun getTaskByName(taskName: String): TaskModel? {
        return taskManagerDao.getTaskByName(taskName)?.toDomain()
    }

    override suspend fun getTask(id: Int): TaskModel {
        return taskManagerDao.getTaskById(id).toDomain()
    }

    override suspend fun delete(taskModel: TaskModel) {
        taskManagerDao.delete(taskModel.toData())
    }

    override suspend fun saveImage(taskModel: TaskModel) {
        taskManagerDao.insertTask(taskModel.toData())
    }

}