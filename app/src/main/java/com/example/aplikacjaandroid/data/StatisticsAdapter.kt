package com.example.aplikacjaandroid.data

import android.content.Context
import android.util.Log
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter


data class StatisticsItem(
    val label: String,
    val value: BigDecimal,
    //val color: Color

)
class StatisticsAdapter( private val context: Context) {

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

    private fun getExpensesFromPeriod(startDate: LocalDate, endDate: LocalDate): List<ItemData>{



        val resultList: List<ItemData> = expensesList.filter{
            val itemDate = LocalDate.parse(it.date, dateFormat)
            itemDate >= startDate && itemDate <= endDate}

        Log.d("TEST", resultList.toString())
        return resultList
    }

    private fun getRevenuesFromPeriod(startDate: LocalDate, endDate: LocalDate): List<ItemData>{

        val resultList: List<ItemData> = revenuesList.filter{
            val itemDate = LocalDate.parse(it.date, dateFormat)
            itemDate >= startDate && itemDate <= endDate}

        return resultList
    }

    private fun groupItemsIntoStatisticItems(items: List<ItemData>): List<StatisticsItem>{

        // TODO(add colors for statistics Item)

        val categories = listOf(0,1,2,3,4,5,6)

        val resultList = mutableListOf<StatisticsItem>()

        categories.forEach(){ category ->

            val tempList = items.filter{ it.imageResource == category}
            if(!tempList.isNullOrEmpty()) {
                val total: BigDecimal =
                    tempList.map { it.amount }.reduce { acc, amount -> acc + amount }

                resultList.add(StatisticsItem(category.toString(), total))
            }
        }
        return resultList.toList()
    }

    fun getExpensesStatisticsFromPeriod(startDate: LocalDate, endDate: LocalDate): List<StatisticsItem>{

        return groupItemsIntoStatisticItems(getExpensesFromPeriod(startDate, endDate))
    }

    fun getRevenuesStatisticsFromPeriod(startDate: LocalDate, endDate: LocalDate): List<StatisticsItem>{

        return groupItemsIntoStatisticItems(getExpensesFromPeriod(startDate, endDate))

    }

    fun getAllExpenses(): List<ItemData>{
        return expensesList
    }

    fun getAllRevenues(): List<ItemData>{
        return revenuesList
    }
}