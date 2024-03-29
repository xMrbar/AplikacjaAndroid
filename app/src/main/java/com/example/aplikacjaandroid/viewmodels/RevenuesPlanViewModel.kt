package com.example.aplikacjaandroid.viewmodels
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.aplikacjaandroid.Counter
import com.example.aplikacjaandroid.data.DataBaseManager
import com.example.aplikacjaandroid.data.FileManager
import kotlinx.coroutines.flow.MutableStateFlow


class RevenuesPlanViewModel(private val context: Context): ViewModel() {
    private val counter: Counter = Counter(context)
    private val fileManager = FileManager("revenuesPlan.txt")

    val planowaneDochodyWMiesiacuKwota = MutableStateFlow(counter.countRevenuesPlan().toString() + "zł")
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
            planowaneDochodyWMiesiacuKwota.value = counter.countRevenuesPlan().toString() + "zł"
            dataBaseManager.removeItemFromDataBase("revenuesPlan", wpis)
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
