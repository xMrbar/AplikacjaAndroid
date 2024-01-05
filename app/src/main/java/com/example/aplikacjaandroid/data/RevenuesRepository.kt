package com.example.aplikacjaandroid.data

import android.content.Context

class RevenuesRepository (val context: Context) {

    private val fileManager: FileManager = FileManager("revenues.txt")

    fun getItemData(): List<ItemData>{

        return fileManager.readItemsFromFile(context)

    }


}