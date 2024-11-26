package com.example.addtask.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.addtask.databinding.ActivityAddTaskBinding

class AddTaskActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityAddTaskBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}