package com.example.aplikacjaandroid.data

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

    private fun maxIndex(context: Context):String {
        val tmpList = readItemsFromFile(context)
        var index = -1
        if(tmpList.isEmpty()) {
            index = 1
        }
        else {
            index = tmpList[tmpList.size - 1].id + 1
        }
        return index.toString()
    }

    fun appendToFile(s: String, context: Context): String {
        val externalDir = context.getExternalFilesDir(null)
        val file = File(externalDir, plik)
        val index = maxIndex(context)

        try {
            val fileWriter = FileWriter(file, true)
            fileWriter.append("$index;$s\n")
            fileWriter.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "$index;$s"
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
        val items = mutableListOf<ItemData>()
        while (line != null) {
            val parts = line.split(";")
            val id = parts[0]
            val title = parts[1]
            val date = parts[2]
            val money = parts[3]
            val type = parts[4]
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
            items.add(ItemData(id.toInt(), typeInt, title, BigDecimal(money), date))
            line = reader.readLine()
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
                val type = if (item.imageResource == 0) {
                    "CAR"
                } else if(item.imageResource == 1) {
                    "ELECTRICITY"
                } else if(item.imageResource == 2) {
                    "INCOME"
                } else if(item.imageResource == 3) {
                    "TRAVEL"
                } else if(item.imageResource == 4) {
                    "HOME"
                } else if(item.imageResource == 5) {
                    "CLOTHES"
                } else {
                    "AUTO"
                }
                outputStream.write((
                        item.id.toString() + ";"
                        + item.text + ";"
                        + item.date + ";"
                        + item.amount.toString() + ";"
                        + type + "\n"
                        ).toByteArray())
            }
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun writeStringItemsToFile(context: Context, items: List<String>)
    {
        val externalDir = context.getExternalFilesDir(null)
        val file = File(externalDir, plik)

        try {
            val outputStream = FileOutputStream(file)
            for(item in items) {
                outputStream.write("$item\n".toByteArray())
            }
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}