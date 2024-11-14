package com.example.cleanarchitecture.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cleanarchitecture.data.database.dao.TaskManagerDao
import com.example.cleanarchitecture.data.dto.TaskDto


@Database(entities = [TaskDto::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun taskManagerDao(): TaskManagerDao
}