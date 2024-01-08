package com.example.aplikacjaandroid.model

import android.content.Context
import android.util.Log
import com.example.aplikacjaandroid.data.ExpensesRepository
import com.example.aplikacjaandroid.data.RevenuesRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
class HistoryAdapter( private val context: Context) {

    private val expensesRepo = ExpensesRepository(context)
    private val revenuesRepo = RevenuesRepository(context)
    private lateinit var expensesList: List<ItemData>
    private lateinit var revenuesList: List<ItemData>

    val dateFormat = DateTimeFormatter.ofPattern("[d][dd].[M][MM].yyyy")

    fun init(){
        expensesList = expensesRepo.getItemData()
        revenuesList = revenuesRepo.getItemData()

        Log.d("TEST", expensesList.toString() + revenuesList.toString())

    }

    fun getAllExpenses(): List<ItemData>{
        return expensesList
    }

    fun getAllRevenues(): List<ItemData>{
        return revenuesList
    }

    fun getExpensesFromMonth(chosen_month: Int, chosen_year: Int): List<ItemData>{

        val resultList: List<ItemData> = expensesList.filter{
            val itemDate = LocalDate.parse(it.date, dateFormat)
            itemDate.month.value == chosen_month && chosen_year == itemDate.year}
        Log.d("TEST", resultList.toString())
        return resultList
    }
    fun getExpensesFromYear(chosen_year: Int): List<ItemData>{

        val resultList: List<ItemData> = expensesList.filter{
            val itemDate = LocalDate.parse(it.date, dateFormat)
            chosen_year == itemDate.year}
        Log.d("TEST", resultList.toString())
        return resultList
    }

    fun getRevenuesFromMonth(chosen_month: Int, chosen_year: Int): List<ItemData>{

        val resultList: List<ItemData> = revenuesList.filter{
            val itemDate = LocalDate.parse(it.date, dateFormat)
            itemDate.month.value == chosen_month && chosen_year == itemDate.year}
        Log.d("TEST", resultList.toString())
        return resultList
    }

    fun getRevenuesFromYear(chosen_year: Int): List<ItemData>{

        val resultList: List<ItemData> = revenuesList.filter{
            val itemDate = LocalDate.parse(it.date, dateFormat)
            chosen_year == itemDate.year}
        Log.d("TEST", resultList.toString())
        return resultList
    }
}