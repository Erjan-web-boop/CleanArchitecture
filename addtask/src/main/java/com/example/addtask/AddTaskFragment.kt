package com.example.addtask

import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.TaskUI
import com.example.addtask.databinding.FragmentAddTaskBinding
import com.example.cleanarchitecture.presentation.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTaskFragment : BaseFragment<FragmentAddTaskBinding>(
    R.layout.fragment_add_task, FragmentAddTaskBinding::bind) {

    private val viewModel: AddTaskViewModel by viewModel()
    private var imageUri: String = ""

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                imageUri = uri.toString()
                binding.ivImage.setImageURI(uri)
            }
        }

    override fun setupListener() {
        binding.ivImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.btnSave.setOnClickListener {
            val taskName = binding.etTaskName.text.toString()
            val taskDate = binding.etTaskDate.text.toString()

            viewModel.isTaskInserted.observe(viewLifecycleOwner) { isInserted ->
                if (isInserted) {
                }
            }

            if (taskName.isBlank() || taskDate.isBlank()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val taskUI = TaskUI(0, taskName, taskDate, imageUri)
            viewModel.insertTask(taskUI)
        }
    }

    override fun setupObserver() {
        runDefaultLaunch {
            viewModel.isLoading.collectLatest { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

                runDefaultLaunch{
                    viewModel.insertMessageFlow.collectLatest { message ->
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            viewModel.clearError()
                        }
                    }
                }
            }
        }
    }
}
