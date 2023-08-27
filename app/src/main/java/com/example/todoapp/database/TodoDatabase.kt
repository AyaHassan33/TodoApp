package com.example.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.database.dao.TodoDao
import com.example.todoapp.database.model.TodosData

@Database(entities = [TodosData::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object{
        //static
        private var instance :TodoDatabase?=null
        //kotlin coroutines without mainThread
        fun getInstance(context : Context):TodoDatabase{
            if (instance == null){
                //initialize
                instance = Room.databaseBuilder(context.applicationContext,
                    TodoDatabase::class.java,
                    "tasksDB")
                    .allowMainThreadQueries()  // allow query in main thread
                    .fallbackToDestructiveMigration() //Migration ---> Move from version to version
                    .build()
            }
            return instance!!
        }
    }
}