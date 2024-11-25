package com.example.cleanarchitecture.presentation.addtask

import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.databinding.FragmentAddTaskBinding
import com.example.cleanarchitecture.presentation.uimodule.TaskUI
import com.example.cleanarchitecture.presentation.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTaskFragment : BaseFragment<FragmentAddTaskBinding>(
    R.layout.fragment_add_task, FragmentAddTaskBinding::bind) {

    private val viewmodel by viewModel<AddTaskViewModel>()
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

            viewmodel.isTaskInserted.observe(viewLifecycleOwner) { isInserted ->
                if (isInserted) {
                    findNavController().navigate(R.id.taskListFragment)
                }
            }

            if (taskName.isBlank() || taskDate.isBlank()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val taskUI = TaskUI(0, taskName, taskDate, imageUri)
            viewmodel.insertTask(taskUI)
        }
    }

    override fun setupObserver() {
        runDefaultLaunch {
            viewmodel.isLoading.collectLatest { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

                runDefaultLaunch{
                    viewmodel.insertMessageFlow.collectLatest { message ->
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            viewmodel.clearError()
                        }
                    }
                }
            }
        }
    }
}
