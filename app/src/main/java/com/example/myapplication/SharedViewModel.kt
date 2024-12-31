import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.contact.CalendarSingleDay
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class Date(
    val year: Int,
    val month: Int,
    val day: Int,
    val weekOfDay: String,
)

data class DateSelection (
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
)

class SharedViewModel : ViewModel() {
    private val _startDate = MutableLiveData<Date>()
    private val _endDate = MutableLiveData<Date>()
    private val _dateSelection = MutableLiveData<DateSelection>()
    private val _dateDifference = MutableLiveData<Long>()
    private val _dateFocused = MutableLiveData<CalendarSingleDay>()
    private val _calendarDescriptions = MutableLiveData<MutableMap<String, String>>(mutableMapOf())

    val startDate: LiveData<Date> get() = _startDate
    val endDate: LiveData<Date> get() = _endDate
    val dateSelection: LiveData<DateSelection> get() = _dateSelection
    val dateDifference: LiveData<Long> get() = _dateDifference
    val dateFocused: LiveData<CalendarSingleDay> get() = _dateFocused
    val calendarDescriptions: LiveData<MutableMap<String, String>> get() = _calendarDescriptions

    fun setFocusedDate(date: CalendarSingleDay){
        _dateFocused.value = date
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setStartDate(date: Date) {
        _startDate.value = date
        val selection = _dateSelection.value?: DateSelection()
        _dateSelection.value = selection.copy(startDate = LocalDate.of(date.year, date.month+1, date.day))
        computeDateDifference()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setEndDate(date: Date) {
        _endDate.value = date
        val selection = _dateSelection.value ?: DateSelection()
        _dateSelection.value = selection.copy(endDate = LocalDate.of(date.year, date.month + 1, date.day))
        computeDateDifference()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun computeDateDifference() {
        val startDate = _dateSelection.value?.startDate
        val endDate = _dateSelection.value?.endDate

        _dateDifference.value = if (startDate != null && endDate != null) {
            ChronoUnit.DAYS.between(startDate, endDate) // Compute difference in days
        } else {
            -1 // No difference if either date is not set
        }
    }

    fun updateCalendarDescription(month: Int, day: Int, description: String) {
        Log.v("update", month.toString()+'.'+day.toString())
        _calendarDescriptions.value?.let { currentDescriptions ->
            currentDescriptions[month.toString()+'.'+day.toString()] = description
            _calendarDescriptions.value = currentDescriptions
        }
    }

    fun getCalendarDescription(month: Int, day: Int): String {
        Log.v("get", month.toString()+'.'+day.toString())
        return _calendarDescriptions.value?.get(month.toString()+'.'+day.toString())?: ""
    }
}