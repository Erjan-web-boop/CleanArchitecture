package com.example.cleanarchitecture.presentation.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.databinding.FragmentTaskListBinding
import com.example.cleanarchitecture.presentation.activity.TaskAdapter
import com.example.cleanarchitecture.presentation.activity.ActivityViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskListFragment : Fragment() {

    private lateinit var binding: FragmentTaskListBinding
    private val taskAdapter = TaskAdapter(emptyList(), ::onItemClick)
    private val viewmodel: ActivityViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        initAdapter()
        initObserver()
        initSwipeToDelete()
        viewmodel.loadTasks()
    }

    private fun setupListener() {
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_addTaskFragment)
        }
    }

    private fun initAdapter() {
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = taskAdapter
    }

    private fun initObserver() {
        viewmodel.viewModelScope.launch {
            viewmodel.tasksFlow.collectLatest {
                taskAdapter.updateTasks(it)
            }
        }
    }

    private fun onItemClick(id: Int) {
        val action = TaskListFragmentDirections.actionTaskListFragmentToTaskDetailFragment(id)
        findNavController().navigate(action)
    }

    private fun initSwipeToDelete() {
        val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val removedTask = taskAdapter.removeTask(position)
                viewmodel.deleteTask(removedTask)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerview)
    }
}