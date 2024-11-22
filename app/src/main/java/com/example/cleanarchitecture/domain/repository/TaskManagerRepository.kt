package com.example.cleanarchitecture.domain.repository




import com.example.cleanarchitecture.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface TaskManagerRepository {

    suspend fun insertTask(taskModel: TaskModel)
    suspend fun getAllTask(): Flow<List<TaskModel>>
    suspend fun getTaskByName(taskName: String): TaskModel ?
    suspend fun getTask(id:Int): TaskModel
    suspend fun delete(taskModel: TaskModel)
    suspend fun saveImage(taskModel: TaskModel)

}