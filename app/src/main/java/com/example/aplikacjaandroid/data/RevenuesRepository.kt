package com.example.aplikacjaandroid.data

import android.content.Context
import android.util.Log
import com.example.aplikacjaandroid.model.ItemData

/*
 * Repository class for revenues.
 * Data is loaded form local file "revenues.txt" using FileManager class
 */
class RevenuesRepository (val context: Context) {

    private val fileManager: FileManager = FileManager("revenues.txt")
    private final val TAG = "RevenuesRepository"

    // returns a list of ItemData objects based on revenues from file
    fun getItemData(): List<ItemData>{

        val resultList = fileManager.readItemsFromFile(context)
        Log.d(TAG, "Expenses loaded from repo. Result list size: ${resultList.size}" )
        return resultList

    }


}