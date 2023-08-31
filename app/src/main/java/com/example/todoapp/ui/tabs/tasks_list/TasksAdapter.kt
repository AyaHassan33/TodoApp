package com.example.todoapp.ui.tabs.tasks_list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todoapp.OnTaskClickListener
import com.example.todoapp.R
import com.example.todoapp.database.model.TodosData
import com.example.todoapp.databinding.ItemTaskBinding

class TasksAdapter(var tasks:List<TodosData>?=null) :Adapter<TasksAdapter.TaskViewHolder>(){

    var onTaskClickListener:OnTaskClickListener?=null

    class TaskViewHolder(var itemBinding : ItemTaskBinding ):ViewHolder(itemBinding.root){
        fun bind(task:TodosData){
            itemBinding.title.text=task.title
           // itemBinding.description.text=task.description
            itemBinding.todoTime.text=task.dateTime.toString()


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemBinding =ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return TaskViewHolder(itemBinding)
    }

    override fun getItemCount(): Int =tasks?.size?:0

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks!![position])
        if(onTaskClickListener!=null){
          /*  holder.itemBinding.card.setOnLongClickListener(View.OnLongClickListener {

                return@OnLongClickListener true
            })*/
            // lamda expression
            holder.itemBinding.card.setOnLongClickListener {
                onTaskClickListener?.onTaskClick(tasks!![position],position)
                return@setOnLongClickListener true
            }

        }
        if(tasks!![position].isDone){
            holder.itemBinding.btnDone.setBackgroundColor(Color.GREEN)
            holder.itemBinding.title.setTextColor(Color.GREEN)
           // holder.itemBinding.btnDone.setBackgroundResource(R.drawable.)
            holder.itemBinding.btnDone.setImageResource(0)
        }


    }

    fun updateTasks(tasks:List<TodosData>){
        this.tasks=tasks
        notifyDataSetChanged()
    }


}