package com.example.cleanarchitecture.presentation.tasklist


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.addtask.activity.AddTaskActivity
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.databinding.FragmentTaskListBinding
import com.example.cleanarchitecture.presentation.activity.TaskAdapter
import com.example.cleanarchitecture.presentation.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskListFragment : BaseFragment<FragmentTaskListBinding>(
    R.layout.fragment_task_list, FragmentTaskListBinding::bind) {

    private val taskAdapter = TaskAdapter(emptyList(), ::onItemClick)
    private val viewmodel by viewModel<TaskListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initSwipeToDelete()
        viewmodel.loadTasks()
    }

    override fun setupListener() {
        binding.btnNext.setOnClickListener {
            val intent = Intent(requireContext(), AddTaskActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        }
    }

    override fun setupObserver() {
        viewmodel.viewModelScope.launch {
            viewmodel.tasksFlow.collectLatest {
                taskAdapter.updateTasks(it)
            }
        }
    }

    private fun initAdapter() {
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = taskAdapter
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