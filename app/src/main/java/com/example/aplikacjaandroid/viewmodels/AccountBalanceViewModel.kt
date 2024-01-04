package com.example.aplikacjaandroid.viewmodels
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.aplikacjaandroid.Counter
import com.example.aplikacjaandroid.data.DataBaseManager
import com.example.aplikacjaandroid.data.FileManager
import kotlinx.coroutines.flow.MutableStateFlow


class AccountBalanceViewModel(private val context: Context): ViewModel() {
    private val counter: Counter = Counter(context)
    private val fileManager = FileManager("accountBalance.txt")

    val stanKonta = MutableStateFlow(counter.countActualBallance().toString() + "zł")
    val stanKontaNaKoniecMiesiaca = MutableStateFlow(counter.countEstaminatedBallance().toString() + "zł")
    val myItems = MutableStateFlow(fileManager.readItemsFromFile(context))

    val selectedIndex = MutableStateFlow(-1)
    private val dataBaseManager = DataBaseManager()

    fun delete()
    {
        val wpis = myItems.value[selectedIndex.value].copy()
        fileManager.deleteItemFromFile(context, selectedIndex.value)
        selectedIndex.value = -1
        myItems.value = fileManager.readItemsFromFile(context)
        dataBaseManager.removeItemFromDataBase("accountBalance", wpis)
    }

    fun onClick(index: Int)
    {
        selectedIndex.value = if (index == selectedIndex.value) -1 else index
    }

    fun resetIndex()
    {
        selectedIndex.value = -1
    }
}
