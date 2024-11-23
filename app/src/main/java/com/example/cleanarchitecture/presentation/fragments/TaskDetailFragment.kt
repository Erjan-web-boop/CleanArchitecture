package com.example.cleanarchitecture.presentation.fragments


import android.view.View
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.databinding.FragmentTaskDetailBinding
import com.example.cleanarchitecture.presentation.activity.ActivityViewModel
import com.example.cleanarchitecture.presentation.base.BaseFragment
import com.example.cleanarchitecture.presentation.uimodule.TaskUI
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskDetailFragment : BaseFragment<FragmentTaskDetailBinding>(
    R.layout.fragment_task_detail, FragmentTaskDetailBinding::bind) {

    private val viewModel by viewModel<ActivityViewModel>()
    private val args: TaskDetailFragmentArgs by navArgs()
    private var taskUI: TaskUI? = null

    override fun loadTask() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.viewModelScope.launch {
            taskUI = viewModel.getTask(args.taskId)
            binding.progressBar.visibility = View.GONE

            taskUI?.let { task ->
                binding.etTaskName.setText(task.taskName)
                binding.etTaskDate.setText(task.taskDate)

                if (task.imageUri.isNotEmpty()) {
                    try {
                        val uri = android.net.Uri.parse(task.imageUri)
                        binding.ivImage.setImageURI(uri)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        binding.ivImage.setImageResource(R.drawable.ic_image)
                    }
                } else {
                    binding.ivImage.setImageResource(R.drawable.ic_image)
                }
            } ?: run {
                Toast.makeText(requireContext(), "Task not found", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    override fun setupListener() {
        binding.btnSave.setOnClickListener {
            val updatedTaskName = binding.etTaskName.text.toString()
            val updatedTaskDate = binding.etTaskDate.text.toString()

            if (updatedTaskName.isBlank() || updatedTaskDate.isBlank()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val isValidDate = try {
                val formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")
                java.time.LocalDate.parse(updatedTaskDate, formatter)
                true
            } catch (e: Exception) {
                false
            }

            if (!isValidDate) {
                Toast.makeText(requireContext(), "Invalid date format. Use yyyy-MM-dd", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            taskUI?.let { task ->
                val updatedTask = task.copy(
                    taskName = updatedTaskName,
                    taskDate = updatedTaskDate,
                )
                viewModel.viewModelScope.launch {
                    try {
                        viewModel.updateTask(updatedTask)
                        Toast.makeText(requireContext(), "Task updated successfully", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(requireContext(), "Failed to update task", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

