package com.example.todoapp.ui.tabs.tasks_list

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todoapp.OnTaskClickListener
import com.example.todoapp.R
import com.example.todoapp.database.model.TodosData
import com.example.todoapp.databinding.ItemTaskBinding

class TasksAdapter(var tasks:MutableList<TodosData>?=null) :Adapter<TasksAdapter.TaskViewHolder>(){

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

    @RequiresApi(Build.VERSION_CODES.M)
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
        holder.itemBinding.btnDone.setOnClickListener {
            onTaskClickListener?.onButtonDoneClick(tasks!![position],position)
        }
       /* holder.itemBinding.btnDone.setOnClickListener {
            onTaskClickListener?.onButtonDoneClick(tasks!![position])
        }*/
        if(tasks!![position].isDone== true){
            holder.itemBinding.btnDone.setImageResource(R.drawable.icon_done)
            holder.itemBinding.btnDone.setBackgroundColor(Color.TRANSPARENT)
            holder.itemBinding.btnDone.setPadding(8)
            holder.itemBinding.verticalLine.setBackgroundColor(
                holder.itemBinding.verticalLine.resources.getColor(R.color.green_isDone_btn,null)
            )
            holder.itemBinding.title.setTextColor(holder.itemBinding.title.resources.getColor(R.color.green_isDone_btn, null))
            /*holder.itemBinding.btnDone.setBackgroundColor(Color.TRANSPARENT)
            holder.itemBinding.title.setTextColor(Color.GREEN)
           // holder.itemBinding.btnDone.setBackgroundResource(R.drawable.)
            holder.itemBinding.btnDone.setImageResource(0)*/
        }else if (tasks!![position].isDone==false){
            holder.itemBinding.btnDone.setImageResource(R.drawable.ic_check_done)
            holder.itemBinding.btnDone.setBackgroundResource(R.drawable.btn_done_bg)
            holder.itemBinding.btnDone.setPadding(40,16,40,16)
            holder.itemBinding.btnDone.adjustViewBounds=true
            holder.itemBinding.verticalLine.setBackgroundColor(
                holder.itemBinding.verticalLine.resources.getColor(R.color.blue_color_light,null)
            )
            holder.itemBinding.title.setTextColor(holder.itemBinding.title.resources.getColor(R.color.blue_color_light, null))
        }
        if (onItemDeleteClickListener!=null){
            holder.itemBinding.deleteView
                .setOnClickListener {
                    holder.itemBinding.swipeLayout.close(true)
                    onItemDeleteClickListener?.onItemDeleteClick(tasks!![position],position)
                }
        }

    }

    fun updateTasks(tasks:MutableList<TodosData>){
        this.tasks=tasks
        notifyDataSetChanged()
    }
    fun taskDeleted(task: TodosData) {
        val position=tasks?.indexOf(task)
        tasks?.remove(task)
        notifyItemRemoved(position!!)

    }

    var onItemDeleteClickListener:OnItemDeleteClickListener?=null
   interface OnItemDeleteClickListener{
        fun onItemDeleteClick(task:TodosData,position: Int)
    }



}