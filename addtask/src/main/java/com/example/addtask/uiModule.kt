package com.example.addtask

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule: Module = module {

    viewModel { AddTaskViewModel(get()) }

}