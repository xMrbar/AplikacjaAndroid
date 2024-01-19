package com.example.aplikacjaandroid

import android.content.Context
import java.io.File

fun CreateFile(context: Context)
{
    val externalDir = context.getExternalFilesDir(null)
    try {
        var file = File(externalDir, "revenues.txt")
        //file.delete()
        if (!file.exists()) {
            file.createNewFile()
        }
        file = File(externalDir, "expenses.txt")
        //file.delete()
        if (!file.exists()) {
            file.createNewFile()
        }
        file = File(externalDir, "accountBalance.txt")
        //file.delete()
        if (!file.exists()) {
            file.createNewFile()
        }
        file = File(externalDir, "revenuesPlan.txt")
        //file.delete()
        if (!file.exists()) {
            file.createNewFile()
        }
        file = File(externalDir, "expensesPlan.txt")
        //file.delete()
        if (!file.exists()) {
            file.createNewFile()
        }
    }
    catch (e: Exception)
    {
        e.printStackTrace()
    }
}