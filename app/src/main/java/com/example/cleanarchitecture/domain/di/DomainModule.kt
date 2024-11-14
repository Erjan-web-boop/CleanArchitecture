package com.example.cleanarchitecture.domain.di

import com.example.cleanarchitecture.domain.usecase.GetAllNotesUseCase
import com.example.cleanarchitecture.domain.usecase.InsertTaskUseCase
import com.example.cleanarchitecture.presentation.viewmodel.ActivityViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val DomainModule: Module = module {

    factory { InsertTaskUseCase(get()) }
    factory { GetAllNotesUseCase(get()) }
    viewModel { ActivityViewModel(get(), get()) }
}