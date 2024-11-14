package com.example.cleanarchitecture.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.databinding.ActivityMainBinding
import com.example.cleanarchitecture.presentation.uimodule.TaskUI
import com.example.cleanarchitecture.presentation.viewmodel.ActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: ActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {

        binding.btnSave.setOnClickListener {
            val task = binding.tvTask.text.toString()
            val dates = binding.tvDate.text.toString()

            val intent = Intent(this, TaskListActivity::class.java)
            startActivity(intent)

            if (task.isBlank() || dates.isBlank()) {
                Toast.makeText(this, "поля пустые", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.insertTask(taskUI = TaskUI(0, task, dates))
        }

        viewModel.insertMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}