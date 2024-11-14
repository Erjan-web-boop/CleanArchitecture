package com.example.cleanarchitecture.data.di

import androidx.room.Room
import com.example.cleanarchitecture.data.database.AppDatabase
import com.example.cleanarchitecture.data.repositoryImpl.TaskManagerRepositoryImpl
import com.example.cleanarchitecture.domain.repository.TaskManagerRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val DataModule: Module = module {

    single {
        Room.databaseBuilder(get(), AppDatabase::class.java,"TaskDataBase")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get  <AppDatabase>().taskManagerDao() }
    single  <TaskManagerRepository>{ TaskManagerRepositoryImpl(get()) }
}