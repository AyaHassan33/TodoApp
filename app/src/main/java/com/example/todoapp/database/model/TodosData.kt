package com.example.todoapp.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Todos")  //--> change table name in database
data class TodosData(
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    val id :Int?=null,
    @ColumnInfo
    var title:String?=null,
    @ColumnInfo
    var description:String?=null,
    @ColumnInfo
    var dateTime: Date?=null,
    @ColumnInfo
    var isDone:Boolean=false

)
