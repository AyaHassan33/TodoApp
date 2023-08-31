package com.example.todoapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import com.example.todoapp.database.Converters
import com.example.todoapp.database.TodoDatabase
import com.example.todoapp.database.model.TodosData
import com.example.todoapp.databinding.ActivityEditTaskBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
@RequiresApi(Build.VERSION_CODES.N)
class EditTaskActivity : AppCompatActivity() {
    lateinit var binding:ActivityEditTaskBinding
    val calendar =Calendar.getInstance()
    private lateinit var task: TodosData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        task =((intent.getSerializableExtra("Task")as?TodosData)!!)
        showData(task)
        binding.dateContainerEdit.setOnClickListener {
            showDatePickerDialog()
        }
        binding.saveEditTaskBtn.setOnClickListener {
            updateTodoTask()
        }
        binding.btnBack.setOnClickListener {
            finish()
        }

        /*binding.editTaskTitle.setText(intent.getStringExtra("title"))
        binding.descEditText.setText(intent.getStringExtra("desc"))
        binding.selectDateValueEdit.text= intent.getStringExtra("time"*/


    }
    private fun validate():Boolean{
        var isValid =true
        if(binding.editTaskTitle.text.toString().isNullOrBlank()){
            binding.titleContainerEdit.error = "please enter title "
            isValid=false
        }else{
            binding.titleContainerEdit.error = null
        }
        if(binding.descEditText.text.toString().isNullOrBlank()){
            binding.descriptionContainerEdit.error = "please enter description "
            isValid=false
        }else{
            binding.descriptionContainerEdit.error = null
        }
       /* if(binding.selectDateValueEdit.text.toString().isNullOrBlank()){
            binding.dateContainerEdit.error = "please choose date "
            isValid=false
        }else{
            binding.dateContainerEdit.error = null
        }*/
        return isValid
    }

    private fun updateTodoTask() {
        if(!validate()){
            return
        }
        task.title=binding.editTaskTitle.text.toString()
        task.description=binding.descEditText.text.toString()
        task.dateTime=calendar.time
        TodoDatabase.getInstance(this).todoDao().updateTodo(task)
        showInsertDialog()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    private fun showInsertDialog() {
        val alertDialogBuilder= AlertDialog.Builder(this)
            .setMessage("Update successfully")
            .setPositiveButton("OK")
            {
                    dialog, which -> dialog.dismiss()
            }
        alertDialogBuilder.show()
    }

    private fun showDatePickerDialog(){
        // context?.let {
        var dialog = DatePickerDialog(this)
        dialog.setOnDateSetListener{ view, year, month, dayOfMonth ->
            binding.selectDateValueEdit.text ="$year / ${month +1} / $dayOfMonth"
            //  calendar.set(year,month,dayOfMonth)
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,month)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            // to ignore time
            calendar.clearTime()

        }
        dialog.show()
        //      }
    }


    private fun showData(task: TodosData) {
        binding.editTaskTitle.setText(task.title)
        binding.descEditText.setText(task.description)
        val date=converLongToTime(task.dateTime)
        binding.selectDateValueEdit.text=date

    }
    private fun converLongToTime(dateTime: Date?): CharSequence? {
        val dateLong = Converters().dateToTimestamp(dateTime)
        val date=Date(dateLong!!)
        val format =SimpleDateFormat("yyyy/MM/dd")
        return format.format(date)

    }


}