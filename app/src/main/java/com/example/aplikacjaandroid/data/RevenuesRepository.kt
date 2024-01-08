package com.example.aplikacjaandroid.data

import android.content.Context
import com.example.aplikacjaandroid.model.ItemData

class RevenuesRepository (val context: Context) {

    private val fileManager: FileManager = FileManager("revenues.txt")

    fun getItemData(): List<ItemData>{

        return fileManager.readItemsFromFile(context)

    }


}