package com.example.aplikacjaandroid.ui
import android.content.Context
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.aplikacjaandroid.FileManager
import kotlinx.coroutines.flow.MutableStateFlow
import java.math.BigDecimal
import java.util.Calendar
import java.util.Date


class AddNewExpenseViewModel(): ViewModel() {
    var selectedOption1 by mutableStateOf("")
        private set
    var selectedOption2 by mutableStateOf("Kategoria")
        private set
    var tytul by mutableStateOf("")
        private set
    var kwota by mutableStateOf(BigDecimal("0.00"))
        private set
    var expanded by mutableStateOf(false)
    var expanded1 by mutableStateOf(false)
    val optionsType = listOf("CAR", "ELECTRICITY", "TRAVEL", "HOME", "CLOTHES", "TV")
    private val fileManager = FileManager("expenses.txt")

    fun updateTytul(t: String) {
        if (t.length <= 21) {
            tytul = t
        }
    }

    fun updateKwota(t: String) {
        kwota = BigDecimal(t)
    }

    fun updateKategoria(t: String) {
        selectedOption2 = t
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun updateData(state: DatePickerState) {
        if(state.selectedDateMillis == null) {
            selectedOption1 = ""
        }
        else {
            val calendar = Calendar.getInstance()
            val date = Date(state.selectedDateMillis!!)
            calendar.setTime(date)
            selectedOption1 = (calendar.get(Calendar.DAY_OF_MONTH)).toString() + "." +
                    (calendar.get(Calendar.MONTH) + 1).toString() + "." +
                    (calendar.get(Calendar.YEAR)).toString()
        }
    }

    fun updateExpanded(b: Boolean) {
        expanded = b
    }

    fun updateExpanded1(b: Boolean) {
        expanded1 = b
    }

    fun appendToFile(context: Context):Boolean
    {
        if (tytul.isEmpty() || selectedOption1.isEmpty() ||
            selectedOption2.equals("Kategoria") || kwota.toDouble() <= 0)
        {
            return false
        }
        appender(context)
        return true
    }

    private fun appender(context: Context)
    {
        fileManager.appendToFile("$tytul;$selectedOption1;$kwota;$selectedOption2", context)
    }
}
