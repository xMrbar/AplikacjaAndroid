package com.example.aplikacjaandroid.model

import android.content.Context
import android.util.Log
import com.example.aplikacjaandroid.data.ExpensesRepository
import com.example.aplikacjaandroid.data.RevenuesRepository
import com.example.aplikacjaandroid.services.StatisticsServices
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter


data class StatisticsItem(
    val category: Category,
    val value: BigDecimal,

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



        val resultList = mutableListOf<StatisticsItem>()

        Category.values().forEach(){ category ->

            val tempList = items.filter{ StatisticsServices.getEnumFromId(it.imageResource) == category}
            if(!tempList.isNullOrEmpty()) {
                val total: BigDecimal =
                    tempList.map { it.amount }.reduce { acc, amount -> acc + amount }

                resultList.add(StatisticsItem(category, total))
            }
        }
        return resultList.toList()
    }

    fun getExpensesStatisticsFromPeriod(startDate: LocalDate, endDate: LocalDate): List<StatisticsItem>{

        return groupItemsIntoStatisticItems(getExpensesFromPeriod(startDate, endDate))
    }

    fun getRevenuesStatisticsFromPeriod(startDate: LocalDate, endDate: LocalDate): List<StatisticsItem>{

        return groupItemsIntoStatisticItems(getRevenuesFromPeriod(startDate, endDate))

    }

    fun getAllExpenses(): List<ItemData>{
        return expensesList
    }

    fun getAllRevenues(): List<ItemData>{
        return revenuesList
    }


}