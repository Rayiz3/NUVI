package com.example.myapplication.ui.calendar

import Date
import SharedViewModel
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCalendarBinding
import com.example.myapplication.ui.contact.CalendarAdapter
import com.example.myapplication.ui.contact.CalendarSingleDay
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var calendarAdapter: CalendarAdapter

    private lateinit var editText: EditText
    private var selectedDay: CalendarSingleDay? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textTitleView: TextView = binding.calendarTitle
        val textSubtitleView: TextView = binding.calendarSubtitle

        // Set the title
        textTitleView.text = getString(R.string.title_calendar)
        textSubtitleView.text = getString(R.string.subtitle_calendar)

        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val startDateButton: Button = view.findViewById(R.id.startDate)
        val endDateButton: Button = view.findViewById(R.id.endDate)
        val calendar = Calendar.getInstance()

        editText = view.findViewById(R.id.calendar_description_text)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView_calendar)

        // Set the RecyclerView's layout manager
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Start Date Picker
        startDateButton.setOnClickListener {
            val startDatePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val weekOfDay = LocalDate.of(year, month+1, dayOfMonth).dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
                    sharedViewModel.setStartDate(Date(year, month, dayOfMonth, weekOfDay))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            startDatePicker.show()
        }

        // End Date Picker
        endDateButton.setOnClickListener {
            val endDatePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val weekOfDay = LocalDate.of(year, month+1, dayOfMonth).dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
                    sharedViewModel.setEndDate(Date(year, month, dayOfMonth, weekOfDay))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            endDatePicker.show()
        }

        // observing dates
        sharedViewModel.startDate.observe(viewLifecycleOwner) { date ->
            startDateButton.text = getString(R.string.date_calendar, date.year%100, date.month + 1, date.day)
            selectedDay = CalendarSingleDay(date.month+1, date.day, date.weekOfDay, "")
        }

        sharedViewModel.endDate.observe(viewLifecycleOwner) { date ->
            endDateButton.text = getString(R.string.date_calendar, date.year%100, date.month + 1, date.day)
        }

        // observing dates difference
        sharedViewModel.dateDifference.observe(viewLifecycleOwner) { difference ->
            val cardViewDescription = view.findViewById<CardView>(R.id.calendar_description)
            val textViewPlaceholder = view.findViewById<TextView>(R.id.label_calendar_placeholder)
            if (difference != null) {
                val startDate = sharedViewModel.startDate.value
                val endDate = sharedViewModel.endDate.value
                if (startDate != null && endDate != null) {
                    val combinedList = if (difference >= 0) {
                        (0..difference.toInt()).map { offset ->
                            val date = LocalDate.of(startDate.year, startDate.month + 1, startDate.day)
                                .plusDays(offset.toLong())
                            CalendarSingleDay(
                                day = date.dayOfMonth,
                                weekOfDay = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN),
                                month = date.monthValue
                            )
                        }
                    } else listOf()

                    calendarAdapter = CalendarAdapter(combinedList, sharedViewModel)
                    recyclerView.adapter = calendarAdapter

                    cardViewDescription.visibility = View.VISIBLE
                    textViewPlaceholder.visibility = View.GONE
                } else {
                    cardViewDescription.visibility = View.GONE
                    textViewPlaceholder.visibility = View.VISIBLE
                }
            }
        }
        sharedViewModel.dateFocused.observe(viewLifecycleOwner) { date ->
            date?.let {
                val previousDate = selectedDay
                if (previousDate != null) {
                    Log.v("date focused", date.month.toString()+'.'+date.day.toString())
                    Log.v("previous text", editText.text.toString())
                    sharedViewModel.updateCalendarDescription(previousDate.month, previousDate.day, editText.text.toString())
                }

                editText.setText(date.description)
                editText.setSelection(editText.length())

                selectedDay = CalendarSingleDay(date.month, date.day, date.weekOfDay, date.description)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}