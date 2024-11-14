package com.example.cleanarchitecture.domain.model

data class TaskModel(
    val id: Int,
    val taskName: String = String(),
    val taskDate: String = String()
)
