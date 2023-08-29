package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.todoapp.databinding.ActivityEditTaskBinding
import java.util.Calendar

class EditTaskActivity : AppCompatActivity() {
    lateinit var binding:ActivityEditTaskBinding
    val calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.editTaskTitle.setText(intent.getStringExtra("title"))
        binding.descEditText.setText(intent.getStringExtra("desc"))
        binding.selectDateValueEdit.text= intent.getStringExtra("time")


    }
}