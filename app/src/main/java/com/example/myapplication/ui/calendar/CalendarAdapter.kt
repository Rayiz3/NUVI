package com.example.myapplication.ui.contact

import SharedViewModel
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

data class CalendarSingleDay (
    val month: Int,
    val day: Int,
    val weekOfDay: String,
    var description: String = ""
)

class CalendarAdapter(
    private var dataSet: List<CalendarSingleDay>,
    private val sharedViewModel: SharedViewModel
): RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    private var idxDateFocused: Int = 0

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayView: TextView
        val weekOfDayView: TextView
        val singleDayView: View

        init {
            // Define click listener for the ViewHolder's View
            dayView = view.findViewById(R.id.calendar_column_day)
            weekOfDayView = view.findViewById(R.id.calendar_column_week)
            singleDayView = view.findViewById(R.id.calendar_single_date)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.calendar_column_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewholder: ViewHolder, position: Int) {
        val item = dataSet[position]
        viewholder.dayView.text = item.day.toString()
        viewholder.weekOfDayView.text = item.weekOfDay

        // color changes
        if (idxDateFocused == position) {
            viewholder.singleDayView.setBackgroundColor(
                viewholder.itemView.context.getColor(R.color.main)
            )
        } else {
            viewholder.singleDayView.setBackgroundColor(
                viewholder.itemView.context.getColor(R.color.subDark)
            )
        }

        viewholder.singleDayView.setOnClickListener {
            var prevIdx = idxDateFocused
            idxDateFocused = viewholder.bindingAdapterPosition

            notifyItemChanged(prevIdx)
            notifyItemChanged(idxDateFocused)

            if (viewholder.bindingAdapterPosition != RecyclerView.NO_POSITION) {
                sharedViewModel.setFocusedDate(
                    CalendarSingleDay(
                        month = item.month,
                        day = item.day,
                        weekOfDay = item.weekOfDay,
                        description = sharedViewModel.getCalendarDescription(item.month, item.day)
                    )
                )
            }
        }
    }

    override fun getItemCount() = dataSet.size
}