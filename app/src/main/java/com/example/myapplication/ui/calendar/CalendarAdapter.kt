package com.example.myapplication.ui.contact

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
    val day: Int,
    val weekOfDay: String,
)

class CalendarAdapter(
    private var dataSet: List<CalendarSingleDay>,
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
            Log.v("idxDateFocused", idxDateFocused.toString())
            Log.v("position", viewholder.bindingAdapterPosition.toString())
            var prevIdx = idxDateFocused
            idxDateFocused = viewholder.bindingAdapterPosition

            notifyItemChanged(prevIdx)
            notifyItemChanged(idxDateFocused)
        }
    }

    override fun getItemCount() = dataSet.size
}