package com.example.todoapp.ui.tabs.tasks_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.database.TodoDatabase

class TodoListFragment : Fragment() {

    lateinit var recyclerView :RecyclerView
    lateinit var adapter :TasksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view:View){
        recyclerView= view.findViewById(R.id.recycler_view_task)
        adapter = TasksAdapter(null)
        recyclerView.adapter= adapter
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
            adapter.updateTasks(tasks)
        }
    }

}