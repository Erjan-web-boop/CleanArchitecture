package com.example.cleanarchitecture.presentation.activity


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitecture.databinding.ItemTaskBinding
import com.example.cleanarchitecture.presentation.uimodule.TaskUI


class TaskAdapter(private var taskList: List<TaskUI>):RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(private val binding: ItemTaskBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(taskUI: TaskUI){
            binding.tvTask1.text = taskUI.taskName
            binding.tvDate.text = taskUI.taskDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }
    fun updateTasks(newTasks: List<TaskUI>) {
        taskList = newTasks
        notifyDataSetChanged()
    }

}