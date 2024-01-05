package com.example.aplikacjaandroid.data

import android.content.Context

class ExpensesRepository (val context: Context) {

    private val fileManager: FileManager = FileManager("expenses.txt")

    fun getItemData(): List<ItemData>{

        return fileManager.readItemsFromFile(context)

    }


}