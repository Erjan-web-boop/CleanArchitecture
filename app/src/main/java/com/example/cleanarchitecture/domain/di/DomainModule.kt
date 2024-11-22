package com.example.cleanarchitecture.domain.di

import com.example.cleanarchitecture.domain.usecase.DeleteTaskUseCase
import com.example.cleanarchitecture.domain.usecase.GetAllNotesUseCase
import com.example.cleanarchitecture.domain.usecase.GetTaskUseCase
import com.example.cleanarchitecture.domain.usecase.InsertTaskUseCase
import com.example.cleanarchitecture.domain.usecase.SaveImageUseCase
import com.example.cleanarchitecture.domain.usecase.UpdateTaskUseCase
import com.example.cleanarchitecture.presentation.activity.ActivityViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val DomainModule: Module = module {

    factory { InsertTaskUseCase(get()) }
    factory { GetAllNotesUseCase(get()) }
    factory { GetTaskUseCase(get()) }
    factory { DeleteTaskUseCase(get()) }
    factory { SaveImageUseCase(get()) }
    factory { UpdateTaskUseCase(get()) }
}