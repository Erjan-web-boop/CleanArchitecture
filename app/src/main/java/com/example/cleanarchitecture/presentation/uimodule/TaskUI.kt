package com.example.cleanarchitecture.presentation.uimodule

import com.example.domain.model.TaskModel

data class TaskUI(
    val id: Int,
    val taskName: String,
    val taskDate: String,
    val imageUri: String
)

fun TaskUI.toDomain() = TaskModel(id, taskName, taskDate, imageUri)
fun TaskModel.toUi() = TaskUI(id, taskName, taskDate, imageUri)