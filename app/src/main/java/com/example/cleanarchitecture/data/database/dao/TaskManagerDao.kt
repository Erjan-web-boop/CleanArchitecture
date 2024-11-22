package com.example.cleanarchitecture.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.cleanarchitecture.data.dto.TaskDto
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskManagerDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertTask(taskDto: TaskDto)

    @Delete
    suspend fun delete(taskDto: TaskDto)

    @Query("SELECT * FROM taskdto")
    fun  getAllTasks(): Flow<List<TaskDto>>

    @Query("SELECT * FROM taskdto WHERE taskName = :taskName LIMIT 1")
    suspend fun getTaskByName(taskName: String): TaskDto?

    @Query("SELECT * FROM TASKDTO WHERE id = :id")
    suspend fun getTaskById(id:Int): TaskDto
}