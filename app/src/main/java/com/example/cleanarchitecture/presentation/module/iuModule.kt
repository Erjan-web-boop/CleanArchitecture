package com.example.cleanarchitecture.presentation.module

import com.example.cleanarchitecture.presentation.addtask.AddTaskViewModel
import com.example.cleanarchitecture.presentation.detail.TaskDetailViewModel
import com.example.cleanarchitecture.presentation.tasklist.TaskListViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val iuModule: Module = module {

    factory { AddTaskViewModel(get()) }
    factory { TaskDetailViewModel(get(), get()) }
    factory { TaskListViewModel(get(), get()) }
}