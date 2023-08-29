package com.example.todoapp

import com.example.todoapp.database.model.TodosData

interface OnTaskClickListener {
    fun onTaskClick(task:TodosData,position:Int)
}