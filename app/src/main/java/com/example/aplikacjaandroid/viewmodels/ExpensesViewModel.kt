package com.example.aplikacjaandroid.viewmodels
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.aplikacjaandroid.Counter
import com.example.aplikacjaandroid.data.DataBaseManager
import com.example.aplikacjaandroid.data.FileManager
import kotlinx.coroutines.flow.MutableStateFlow


class ExpensesViewModel(private val context: Context): ViewModel() {
    private val counter: Counter = Counter(context)
    private val fileManager = FileManager("expenses.txt")

    val wydatkiWTymMiesiacu = MutableStateFlow((counter.countExpensesPlan() - counter.countExpensesThisMonth()).toString() + "zł")
    val planowaneWydatkiWMiesiacu = MutableStateFlow(counter.countExpensesPlan().toString() + "zł")

    val myItems = MutableStateFlow(fileManager.readItemsFromFile(context))

    val selectedIndex = MutableStateFlow(-1)
    private val dataBaseManager = DataBaseManager()

    fun delete()
    {
        if (selectedIndex.value >= 0) {
            val wpis = myItems.value[selectedIndex.value].copy()

            fileManager.deleteItemFromFile(context, selectedIndex.value)
            selectedIndex.value = -1
            myItems.value = fileManager.readItemsFromFile(context)
            wydatkiWTymMiesiacu.value =
                (counter.countExpensesPlan() - counter.countExpensesThisMonth()).toString() + "zł"
            dataBaseManager.removeItemFromDataBase("expenses", wpis)
        }
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
