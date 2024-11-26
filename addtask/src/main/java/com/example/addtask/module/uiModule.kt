package com.example.addtask.module

import com.example.addtask.AddTaskViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule: Module = module {

    viewModel { AddTaskViewModel(get()) }

}