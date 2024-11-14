package com.example.cleanarchitecture.presentation.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.databinding.ActivityTaskListBinding
import com.example.cleanarchitecture.presentation.uimodule.TaskUI
import com.example.cleanarchitecture.presentation.viewmodel.ActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class TaskListActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityTaskListBinding.inflate(layoutInflater)
    }
    private val viewModel: ActivityViewModel by viewModel()
    private val taskAdapter = TaskAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        initAdapter()
        initObserver()
        viewModel.loadTasks()
    }

    private fun initAdapter() {
        binding.rvTask.adapter = taskAdapter
    }

    private fun initObserver() {
        viewModel.tasks.observe(this, Observer { tasks ->
            taskAdapter.updateTasks(tasks)
        })
    }
}
