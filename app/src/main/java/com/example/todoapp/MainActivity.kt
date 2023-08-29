package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.todoapp.ui.tabs.AddTodoBottomSheetFragment
import com.example.todoapp.ui.tabs.SettingsFragment
import com.example.todoapp.ui.tabs.tasks_list.TodoListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var addTodoButton:FloatingActionButton
    var todoListFragment : TodoListFragment = TodoListFragment()
    var todoSettingsFragment :SettingsFragment = SettingsFragment()
    lateinit var txtToolobar:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }
    private fun initViews(){
        bottomNavigationView=findViewById(R.id.bottom_navigation_view)
        txtToolobar=findViewById(R.id.txt_toolbar)
        bottomNavigationView.setOnItemSelectedListener {
//            equal switch in java
            when(it.itemId){
                R.id.navigation_list->{
                    pushFragment(todoListFragment)
                    txtToolobar.text= getString(R.string.to_do_list)
                }
                R.id.navigation_settings->{
                    pushFragment(todoSettingsFragment)
                    txtToolobar.text= getString(R.string.settings)
            }
            }
          /*  if(it.itemId == R.id.navigation_list){
                pushFragment(todoListFragment)
            }else if(it.itemId ==R.id.navigation_settings){
                pushFragment(todoSettingsFragment)
            }*/
            return@setOnItemSelectedListener true
        }
        bottomNavigationView.selectedItemId = R.id.navigation_list
        addTodoButton=findViewById(R.id.add_todo_button)
        addTodoButton.setOnClickListener {
            showAddTodoBottomSheet()
        }
    }
    private fun showAddTodoBottomSheet(){
        val addBottomSheet =AddTodoBottomSheetFragment()
        addBottomSheet.onTaskAddListener= AddTodoBottomSheetFragment.OnTaskAddListener {
            /*Snackbar.make(CoordinatorLayout(this@MainActivity),"Task Added Successfully",Snackbar.LENGTH_LONG)
                .setAction( "UNDO",null).show()*/
            Toast.makeText(this@MainActivity, "Task Added Successfully", Toast.LENGTH_LONG).show()

            // notify tasks list fragment
            todoListFragment.loadTasks()

        }
        addBottomSheet.show(supportFragmentManager,"Add-Todo")
    }
    private fun pushFragment(fragment:Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
            .commit()
    }
}