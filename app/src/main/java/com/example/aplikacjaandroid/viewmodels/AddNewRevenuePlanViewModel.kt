package com.example.aplikacjaandroid.viewmodels
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.aplikacjaandroid.data.DataBaseManager
import com.example.aplikacjaandroid.data.FileManager
import java.math.BigDecimal


class AddNewRevenuePlanViewModel(): ViewModel() {
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
    val optionsType = listOf("INCOME")
    private val fileManager = FileManager("revenuesPlan.txt")
    private val dataBaseManager = DataBaseManager()

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
        val appended = fileManager.appendToFile("$tytul;$selectedOption1;$kwota;$selectedOption2", context)
        dataBaseManager.addItemToDataBase("revenuesPlan", appended)
    }
}
