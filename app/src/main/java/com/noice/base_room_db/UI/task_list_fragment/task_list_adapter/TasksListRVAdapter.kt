package com.noice.base_room_db.UI.task_list_fragment.task_list_adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.noice.base_room_db.R


import com.noice.base_room_db.databinding.FragmentTaskItemBinding
import com.noice.base_room_db.DATA.room_setup.PriorityLevel
import com.noice.base_room_db.DATA.room_setup.Task

/**
 *
 *
 */
class TasksListRVAdapter()
    : ListAdapter<Task, TasksListRVAdapter.ViewHolder>(DiffCallBack()) {

    lateinit var listener : onTaskClick

    interface onTaskClick{
        fun noiceClickImplement(taskId : Long)
    }


    fun setOnItemClick(mlistener: onTaskClick){
        listener = mlistener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(FragmentTaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    inner class ViewHolder(val binding: FragmentTaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.titleTv
        val description: TextView = binding.descTv
        val priority : CardView = binding.priorityCv

        fun bind(task: Task) {

            when (task.priority) {
                PriorityLevel.High.ordinal -> {
                    priority.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.color_high
                        )
                    )
                }
                PriorityLevel.Medium.ordinal -> {
                    priority.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.color_medium
                        )
                    )
                }
                PriorityLevel.Low.ordinal -> {
                    priority.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.color_low
                        )
                    )
                }
            }

            title.text = task.title
            description.text = task.desc
        }

        init{
            itemView.setOnClickListener {
                listener.noiceClickImplement(getItem(absoluteAdapterPosition).id)

            }
        }

        override fun toString(): String {
            return super.toString() + " '" + description.text + "'"
        }
    }

    class DiffCallBack(): DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }

}