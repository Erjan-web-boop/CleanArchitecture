package com.example.cleanarchitecture.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.databinding.FragmentTaskDetailBinding
import com.example.cleanarchitecture.presentation.activity.ActivityViewModel
import com.example.cleanarchitecture.presentation.uimodule.TaskUI
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskDetailFragment : Fragment() {

    private lateinit var binding: FragmentTaskDetailBinding
    private val viewModel: ActivityViewModel by viewModel()
    private val args: TaskDetailFragmentArgs by navArgs()
    private var taskUI: TaskUI? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadTask()
        setupListeners()
    }

    private fun loadTask() {
        viewModel.viewModelScope.launch {
            taskUI = viewModel.getTask(args.taskId)
            taskUI?.let { task ->
                binding.etTaskName.setText(task.taskName)
                binding.etTaskDate.setText(task.taskDate)

                if (task.imageUri.isNotEmpty()) {
                    binding.ivImage.setImageURI(android.net.Uri.parse(task.imageUri))
                } else {
                    binding.ivImage.setImageResource(R.drawable.ic_image)
                }
            }
        }
    }

    private fun setupListeners() {
        binding.btnSave.setOnClickListener {
            val updatedTaskName = binding.etTaskName.text.toString()
            val updatedTaskDate = binding.etTaskDate.text.toString()


            if (updatedTaskName.isBlank() || updatedTaskDate.isBlank()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            taskUI?.let { task ->
                val updatedTask = task.copy(
                    taskName = updatedTaskName,
                    taskDate = updatedTaskDate,
                )
                viewModel.viewModelScope.launch {
                    viewModel.updateTask(updatedTask)
                    Toast.makeText(requireContext(), "Task updated successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
