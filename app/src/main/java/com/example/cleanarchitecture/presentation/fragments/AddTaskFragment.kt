package com.example.cleanarchitecture.presentation.fragments



import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.databinding.FragmentAddTaskBinding
import com.example.cleanarchitecture.presentation.uimodule.TaskUI
import com.example.cleanarchitecture.presentation.activity.ActivityViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class AddTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private val viewmodel: ActivityViewModel by viewModel()
    private var imageUri: String = ""
    private var taskTime: Long = 0L

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                imageUri = uri.toString()
                binding.ivImage.setImageURI(uri)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }

    private fun setupListener() {
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

        viewmodel.viewModelScope.launch {
            viewmodel.insertMessageFlow.collectLatest {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
