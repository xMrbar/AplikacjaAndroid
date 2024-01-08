package com.example.aplikacjaandroid.data

import android.content.Context
import android.util.Log
import com.example.aplikacjaandroid.model.ItemData


/*
 * Repository class for expenses.
 * Data is loaded form local file "expenses.txt" using FileManager class
 */

class ExpensesRepository (val context: Context) {

    private val fileManager: FileManager = FileManager("expenses.txt")
    private final val TAG = "ExpensesRepository"

    // returns a list of ItemData objects based on expenses form file
    fun getItemData(): List<ItemData>{

        val resultList = fileManager.readItemsFromFile(context)
        Log.d(TAG, "Expenses loaded from repo. Result list size: ${resultList.size}" )
        return resultList

    }


}