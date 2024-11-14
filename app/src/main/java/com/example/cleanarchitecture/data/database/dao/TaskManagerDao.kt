package com.example.cleanarchitecture.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.cleanarchitecture.data.dto.TaskDto


@Dao
interface TaskManagerDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertTask(taskDto: TaskDto)

    @Query("SELECT * FROM taskdto")
    suspend fun  getAllTasks(): List<TaskDto>

    @Query("SELECT * FROM taskdto WHERE taskName = :taskName LIMIT 1")
    suspend fun getTaskByName(taskName: String): TaskDto?
}