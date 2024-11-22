package com.example.cleanarchitecture.presentation.module

import com.example.cleanarchitecture.presentation.activity.ActivityViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val iuModule: Module = module {

    factory { ActivityViewModel(get(), get(), get(), get(), get(), get()) }
}