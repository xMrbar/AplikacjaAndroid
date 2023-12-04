package com.example.aplikacjaandroid.ui
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.aplikacjaandroid.FileManager
import com.example.aplikacjaandroid.ui.components.MySelectBox
import kotlinx.coroutines.flow.MutableStateFlow
import java.math.BigDecimal


class AddNewExpensePlanViewModel(): ViewModel() {
    var selectedOption1 by mutableStateOf("Częstość płatności")
        private set
    var selectedOption2 by mutableStateOf("Kategoria")
        private set
    var tytul by mutableStateOf("")
        private set
    var kwota by mutableStateOf(BigDecimal("0.00"))
        private set
    var expanded by mutableStateOf(false)
    var expanded1 by mutableStateOf(false)
    val optionsDate = listOf("CO MIESIĄC", "CO 3 MIESIĄCE", "CO 6 MIESIĘCY", "CO 12 MIESIĘCY")
    val optionsType = listOf("CAR", "ELECTRICITY", "TRAVEL", "HOME", "CLOTHES", "TV")
    private val fileManager = FileManager("expensesPlan.txt")

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

    fun updateData(t: String) {
        selectedOption1 = t
        expanded = false
    }

    fun updateExpanded(b: Boolean) {
        expanded = b
    }

    fun updateExpanded1(b: Boolean) {
        expanded1 = b
    }

    fun appendToFile(context: Context):Boolean
    {
        if (tytul.isEmpty() || selectedOption1.equals("Częstość płatności") ||
            selectedOption2.equals("Kategoria") || kwota.toDouble() <= 0)
        {
            return false
        }
        appender(context)
        return true
    }

    private fun appender(context: Context)
    {
        fileManager.appendToFile(tytul + ";" + selectedOption1 + ";" + kwota + ";" + selectedOption2, context)
    }
}
