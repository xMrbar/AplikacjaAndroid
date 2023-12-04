package com.example.aplikacjaandroid.ui
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.aplikacjaandroid.Counter
import com.example.aplikacjaandroid.FileManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class ExpensesPlanViewModel(private val context: Context): ViewModel() {
    private val counter: Counter = Counter(context)
    private val fileManager = FileManager("expensesPlan.txt")

    val planowaneWydatkiWMiesiacuKwota = MutableStateFlow(counter.countExpensesPlan().toString() + "zł")
    val myItems = MutableStateFlow(fileManager.readItemsFromFile(context))

    val selectedIndex = MutableStateFlow(-1)

    fun delete()
    {
        fileManager.deleteItemFromFile(context, selectedIndex.value)
        selectedIndex.value = -1
        myItems.value = fileManager.readItemsFromFile(context)
        planowaneWydatkiWMiesiacuKwota.value = counter.countExpensesPlan().toString() + "zł"
    }

    fun onClick(index: Int)
    {
        selectedIndex.value =
            if (index == selectedIndex.value) -1 else index
    }

    fun resetIndex()
    {
        selectedIndex.value = -1
    }
}
