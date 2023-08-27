package com.example.todoapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.database.model.TodosData


@Dao
interface TodoDao {
    @Insert
    fun insertTodo(task:TodosData)
    @Update
    fun updateTodo(task:TodosData)
    @Delete
    fun deleteTodo(task:TodosData)
    @Query("SELECT * FROM todos")
    fun getAllTasks():List<TodosData>

    @Query("select * from todos where dateTime = :dateTime")
    fun getTasksByDate(dateTime :Long):List<TodosData>
}