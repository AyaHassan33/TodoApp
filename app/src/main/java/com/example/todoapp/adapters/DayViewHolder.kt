package com.example.todoapp.adapters

import android.view.View
import android.widget.TextView
import com.example.todoapp.R
import com.kizitonwose.calendar.view.ViewContainer

class DayViewHolder(val calenderDayView: View):ViewContainer(calenderDayView) {
    val dayOfMonthText:TextView=view.findViewById(R.id.day_of_month_text)
    val dayOfWeekText:TextView=view.findViewById(R.id.day_of_week_text)
}