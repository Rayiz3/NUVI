import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Date(
    val year: Int,
    val month: Int,
    val day: Int
)

class SharedViewModel : ViewModel() {
    private val _startDate = MutableLiveData<Date>()
    private val _endDate = MutableLiveData<Date>()

    val startDate: LiveData<Date> get() = _startDate
    val endDate: LiveData<Date> get() = _endDate

    fun setStartDate(date: Date) {
        _startDate.value = date
    }

    fun setEndDate(date: Date) {
        _endDate.value = date
    }
}