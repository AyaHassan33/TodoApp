package com.example.todoapp.ui.tabs


import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.todoapp.database.TodoDatabase
import com.example.todoapp.database.model.TodosData
import com.example.todoapp.databinding.FragmentAddTodoBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddTodoBottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var viewBinding:FragmentAddTodoBottomSheetBinding
    val calendar =Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_add_todo_bottom_sheet, container, false)
        viewBinding=FragmentAddTodoBottomSheetBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.selectDateValue.text="${calendar.get(Calendar.DAY_OF_MONTH)} " +
                "/ ${calendar.get(Calendar.MONTH)+1} / ${calendar.get(Calendar.YEAR)}"
        viewBinding.addTaskBtn
            .setOnClickListener {
                createTask()
            }
        viewBinding.dateContainer
            .setOnClickListener {
                showDatePickerDialog()
            }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDatePickerDialog(){
       // context?.let {
            var dialog = DatePickerDialog(requireContext())
                dialog.setOnDateSetListener{ view, year, month, dayOfMonth ->
                    viewBinding.selectDateValue.text ="$year / ${month +1} / $dayOfMonth"
                    calendar.set(year,month,dayOfMonth)
                    // to ignore time
                    calendar.set(Calendar.HOUR_OF_DAY,0)
                    calendar.set(Calendar.MINUTE,0)
                    calendar.set(Calendar.SECOND,0)
                    calendar.set(Calendar.MILLISECOND,0)

            }
            dialog.show()
  //      }
    }
    private fun validate():Boolean{
        var isValid =true
        if(viewBinding.titleEditText.text.toString().isNullOrBlank()){
            viewBinding.titleContainer.error = "please enter title "
            isValid=false
        }else{
            viewBinding.titleContainer.error = null
        }
        if(viewBinding.descEditText.text.toString().isNullOrBlank()){
            viewBinding.descriptionContainer.error = "please enter description "
            isValid=false
        }else{
            viewBinding.descriptionContainer.error = null
        }
        if(viewBinding.selectDateValue.text.toString().isNullOrBlank()){
            viewBinding.dateContainer.error = "please choose date "
            isValid=false
        }else{
            viewBinding.dateContainer.error = null
        }
        return isValid
    }

    private fun createTask(){
        if(!validate()){
            return
        }
        val task =TodosData(
            title = viewBinding.titleEditText.text.toString(),
            description = viewBinding.descEditText.text.toString(),
            dateTime = calendar.time,  //calender.time
            isDone = false
        )
        TodoDatabase.getInstance(requireContext())
            .todoDao()
            .insertTodo(task)
        onTaskAddListener?.onTaskAdded()
        dismiss()
    }

    var onTaskAddListener:OnTaskAddListener?=null
    fun interface OnTaskAddListener{
        fun onTaskAdded()
    }


}