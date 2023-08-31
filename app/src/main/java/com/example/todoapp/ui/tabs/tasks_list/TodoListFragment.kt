package com.example.todoapp.ui.tabs.tasks_list

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.EditTaskActivity
import com.example.todoapp.OnTaskClickListener
import com.example.todoapp.R
import com.example.todoapp.adapters.DayViewHolder
import com.example.todoapp.base.BaseFragment
import com.example.todoapp.clearTime
import com.example.todoapp.database.TodoDatabase
import com.example.todoapp.database.model.TodosData
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.WeekDayPosition
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.WeekCalendarView
import com.kizitonwose.calendar.view.WeekDayBinder
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TodoListFragment : BaseFragment() {

    lateinit var recyclerView :RecyclerView
    lateinit var adapter :TasksAdapter
    lateinit var calenderView:WeekCalendarView
    var selectedDate:LocalDate?=null
    lateinit var calendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        calenderView=view.findViewById(R.id.calender_view)
        calendar=Calendar.getInstance()
        calenderView.dayBinder =object : WeekDayBinder<DayViewHolder>{
            override fun create(view: View): DayViewHolder {
                return DayViewHolder(view)
            }
            override fun bind(container: DayViewHolder, data: WeekDay) {
                // toString() --> show {Saturday} ... getDisplayName() --> show short char {Sat}
                val day = data

                container.dayOfWeekText.text=data.date.dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    Locale.getDefault() // lacal special language
                )
                container.dayOfMonthText.text = data.date.dayOfMonth.toString()
                container.calenderDayView.setOnClickListener {
                   // if (data.position == WeekDayPosition.OutDate) {
                        val currentSelection = selectedDate
                        if (currentSelection == data.date) {
                            selectedDate = null
                            getTodoByDate(null)
                            calenderView.notifyDateChanged(currentSelection)
                        } else {
                            selectedDate = data.date
                            calenderView.notifyDateChanged(data.date)
                            if (currentSelection != null) {
                                // We need to also reload the previously selected
                                // date so we can REMOVE the selection background.
                                calenderView.notifyDateChanged(currentSelection)
                            }
                        }
                   // }
                }

               /* val textView = container.dayOfMonthText
                textView.text = data.date.dayOfMonth.toString()
                if (data.position == WeekDayPosition.InDate) {
                    // Show the month dates. Remember that views are reused!
                    textView.visibility = View.VISIBLE
                    if (data.date == selectedDate) {
                        // If this is the selected date, show a round background and change the text color.
                        textView.setTextColor(Color.BLUE)
                        calendar.clearTime()
                        getTodoByDate(calendar.time)

                    } else {
                        // If this is NOT the selected date, remove the background and reset the text color.
                        textView.setTextColor(Color.BLACK)
                        textView.background = null
                    }
                } else {
                    // Hide in and out dates
                    textView.visibility = View.INVISIBLE
                }*/
                if (selectedDate == data.date){
                    container.dayOfWeekText.setTextColor(resources.getColor(R.color.blue_color_light,null))
                    container.dayOfMonthText.setTextColor(resources.getColor(R.color.blue_color_light,null))
                    //set date
                    calendar.set(Calendar.DAY_OF_MONTH,selectedDate?.dayOfMonth!!)
                    calendar.set(Calendar.MONTH,selectedDate?.monthValue?.minus(1)!!)
                    calendar.set(Calendar.YEAR,selectedDate?.year!!)
                    // clear time
                    calendar.clearTime()
                    getTodoByDate(calendar.time)

                }else{
                    container.dayOfWeekText.setTextColor(resources.getColor(R.color.black,null))
                    container.dayOfMonthText.setTextColor(resources.getColor(R.color.black,null))
                }


            }
        }

        val currentDate = LocalDate.now()
        val currentMonth = YearMonth.now()
        val startDate = currentMonth.minusMonths(100).atStartOfMonth() // Adjust as needed
        val endDate = currentMonth.plusMonths(100).atEndOfMonth()  // Adjust as needed
        val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
        calenderView.setup(startDate, endDate, firstDayOfWeek)
        calenderView.scrollToWeek(currentDate)
    }

    private fun initViews(view:View){
        recyclerView= view.findViewById(R.id.recycler_view_task)
        adapter = TasksAdapter(null)
        adapter.onTaskClickListener=object :OnTaskClickListener{
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onTaskClick(task: TodosData, position: Int) {
                showMessage("What do you want ? ",
                    "Update",
                    { _, dialog->updateTodoTask(task) },
                    "Make Done !",
                    {_,dialog->makeDone(task)}
                    )

            }

        }
        adapter.onItemDeleteClickListener=object :TasksAdapter.OnItemDeleteClickListener{
            override fun onItemDeleteClick(task: TodosData, position: Int) {
                deleteTaskFromDatabase(task)
                adapter.taskDeleted(task)
            }

        }
        recyclerView.adapter= adapter
    }

    private fun deleteTaskFromDatabase(task: TodosData) {
        showMessage("are you want to delete this task", postActionTitle = "yes",
            postAction = {dialog,_ ->dialog.dismiss()
                TodoDatabase.getInstance(requireContext())
                    .todoDao()
                    .deleteTodo(task)}, negActionTitle = "No",
            negAction = {dialog,_ ->dialog.dismiss()
                reFreshRecycleView()}
        )


    }


    private fun makeDone(task: TodosData) {
        task.isDone=true
        TodoDatabase.getInstance(requireActivity()).todoDao()
            .updateTodo(task)
        reFreshRecycleView()

    }

    private fun reFreshRecycleView() {
        loadTasks()

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateTodoTask(task: TodosData) {
        /*var intent =Intent(activity,EditTaskActivity::class.java)
        intent.putExtra("title",task.title)
        intent.putExtra("desc",task.description)
        intent.putExtra("id",task.id)
        intent.putExtra("isDone",task.isDone)
        intent.putExtra("time",task.dateTime.toString())
        */
        var intent =Intent(requireContext(),EditTaskActivity::class.java)
        intent.putExtra("Task",task)
        startActivity(intent)

    }

    override fun onStart() {
        super.onStart()
        loadTasks()
    }
    fun loadTasks(){
        context?.let{
            val tasks =  TodoDatabase.getInstance(it)
                .todoDao()
                .getAllTasks()
            adapter.updateTasks(tasks.toMutableList())
        }
    }

    fun getTodoByDate(selectedDate: Date?){
       val todoList= if (selectedDate != null)
        TodoDatabase
            .getInstance(requireContext())
            .todoDao()
            .getTasksByDate(selectedDate)
        else
            TodoDatabase
                .getInstance(requireContext())
                .todoDao()
                .getAllTasks()

        adapter.updateTasks(todoList.toMutableList())

    }

}