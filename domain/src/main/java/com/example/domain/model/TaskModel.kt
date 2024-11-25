package com.example.domain.model

import java.util.Date

data class TaskModel(
    val id: Int,
    val taskName: String = String(),
    val taskDate: String = String(),
    val imageUri: String = String()
)
