package com.example.myapplication.ui.calendar

import Date
import SharedViewModel
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCalendarBinding
import java.util.Calendar

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val calendarViewModel =
            ViewModelProvider(this).get(CalendarViewModel::class.java)

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textTitleView: TextView = binding.calendarTitle
        val textSubtitleView: TextView = binding.calendarSubtitle

        textTitleView.text = getString(R.string.title_calendar)
        textSubtitleView.text = getString(R.string.subtitle_calendar)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val startDateButton: Button = view.findViewById(R.id.startDate)
        val endDateButton: Button = view.findViewById(R.id.endDate)
        val calendar = Calendar.getInstance()

        // Start Date Picker
        startDateButton.setOnClickListener {
            val startDatePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    sharedViewModel.setStartDate(Date(year, month, dayOfMonth))
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
                    sharedViewModel.setEndDate(Date(year, month, dayOfMonth))
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
        }

        sharedViewModel.endDate.observe(viewLifecycleOwner) { date ->
            endDateButton.text = getString(R.string.date_calendar, date.year%100, date.month + 1, date.day)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}