package com.example.domain.di

import com.example.domain.usecase.DeleteTaskUseCase
import com.example.domain.usecase.GetAllNotesUseCase
import com.example.domain.usecase.GetTaskUseCase
import com.example.domain.usecase.InsertTaskUseCase
import com.example.domain.usecase.SaveImageUseCase
import com.example.domain.usecase.UpdateTaskUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val DomainModule: Module = module {
    factory { InsertTaskUseCase(get()) }
    factory { GetAllNotesUseCase(get()) }
    factory { GetTaskUseCase(get()) }
    factory { DeleteTaskUseCase(get()) }
    factory { SaveImageUseCase(get()) }
    factory { UpdateTaskUseCase(get()) }
}