package com.example.aplikacjaandroid

import android.content.Context
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.io.InputStreamReader
import java.math.BigDecimal

class FileManager {
    private var plik = ""
    constructor(plik: String) {
        this.plik = plik
    }

    fun appendToFile(s: String, context: Context) {
        val externalDir = context.getExternalFilesDir(null)
        val file = File(externalDir, plik)

        try {
            val fileWriter = FileWriter(file, true)
            fileWriter.append("$s\n")
            fileWriter.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteItemFromFile(context: Context, position: Int)
    {
        val items = readItemsFromFile(context).toMutableList()
        if (position in items.indices)
        {
            items.removeAt(position)
            writeItemsToFile(context, items)
        }
    }

    fun readItemsFromFile(context: Context): List<ItemData>
    {
        val externalDir = context.getExternalFilesDir(null)
        val file = File(externalDir, plik)
        val inputStream = FileInputStream(file)
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String? = reader.readLine()
        var index: Int = 0
        var items = mutableListOf<ItemData>()
        while (line != null) {
            val parts = line.split(";")
            val title = parts[0]
            val date = parts[1]
            val money = parts[2]
            val type = parts[3]
            val typeInt = if (type.contains("CAR", ignoreCase = true)) {
                0
            } else if(type.contains("ELECTRICITY", ignoreCase = true)) {
                1
            } else if(type.contains("INCOME", ignoreCase = true)) {
                2
            } else if(type.contains("TRAVEL", ignoreCase = true)) {
                3
            } else if(type.contains("HOME", ignoreCase = true)) {
                4
            } else if(type.contains("CLOTHES", ignoreCase = true)) {
                5
            } else {
                6
            }
            items.add(ItemData(typeInt, title, BigDecimal(money), date))
            line = reader.readLine()
            index += 1
        }
        inputStream.close()
        return items
    }

    fun writeItemsToFile(context: Context, items: List<ItemData>)
    {
        val externalDir = context.getExternalFilesDir(null)
        val file = File(externalDir, plik)

        try {
            val outputStream = FileOutputStream(file)
            for(item in items) {
                outputStream.write((
                        item.text + ";"
                        + item.date + ";"
                        + item.amount.toString() + ";"
                        + item.imageResource.toString() + "\n"
                        ).toByteArray())
            }
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}