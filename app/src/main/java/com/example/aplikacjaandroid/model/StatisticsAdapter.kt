package com.example.aplikacjaandroid.model

import android.content.Context
import android.util.Log
import com.example.aplikacjaandroid.data.ExpensesRepository
import com.example.aplikacjaandroid.data.RevenuesRepository
import com.example.aplikacjaandroid.services.StatisticsServices
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter


/*
 * This class represents summary of all expenses or revenues from given category
 * value - sum of all expenses/revenues
 */
data class StatisticsItem(
    val category: Category,
    val value: BigDecimal,
    )

/*
 * Adapter to get statistics from list of revenues/expenses for StatisticsScreen
 * Gets revenues/expenses from repositories and returns statistics information based on them
 */

class StatisticsAdapter( private val context: Context) {

    private val expensesRepo = ExpensesRepository(context)
    private val revenuesRepo = RevenuesRepository(context)
    private lateinit var expensesList: List<ItemData>
    private lateinit var revenuesList: List<ItemData>

    val dateFormat = DateTimeFormatter.ofPattern("[d][dd].[M][MM].yyyy")
    private final val TAG = "StatisticsAdapter"


    // load data form repos to adapter
    fun init(){

        expensesList = expensesRepo.getItemData()
        revenuesList = revenuesRepo.getItemData()

        Log.d(TAG,  "Expenses and revenues loaded form file. " +
                " RevenuesListSize: ${revenuesList.size}" +
                " ExpensesListSize: ${expensesList.size}"
        )

    }

    // returns list of expenses form given period of time
    private fun getExpensesFromPeriod(startDate: LocalDate, endDate: LocalDate): List<ItemData>{

        val resultList: List<ItemData> = expensesList.filter{
            val itemDate = LocalDate.parse(it.date, dateFormat)
            itemDate >= startDate && itemDate <= endDate}

        Log.d(TAG, "Expenses form period ${startDate.toString()} - ${endDate.toString()} returned. " +
                "Result list size: ${resultList.size}")
        return resultList
    }

    // returns list of revenues form given period of time
    private fun getRevenuesFromPeriod(startDate: LocalDate, endDate: LocalDate): List<ItemData>{

        val resultList: List<ItemData> = revenuesList.filter{
            val itemDate = LocalDate.parse(it.date, dateFormat)
            itemDate >= startDate && itemDate <= endDate}

        Log.d(TAG, "Expenses form period ${startDate.toString()} - ${endDate.toString()} returned. " +
                "Result list size: ${resultList.size}")
        return resultList
    }


    // returns list of statistics items based on given list of ItemData
    // result list is obtained by grouping items by categories
    private fun groupItemsIntoStatisticItems(items: List<ItemData>): List<StatisticsItem>{

        val resultList = mutableListOf<StatisticsItem>()

        Category.values().forEach(){ category ->

            val tempList = items.filter{ StatisticsServices.getEnumFromId(it.imageResource) == category}
            if(!tempList.isNullOrEmpty()) {
                val total: BigDecimal =
                    tempList.map { it.amount }.reduce { acc, amount -> acc + amount }

                resultList.add(StatisticsItem(category, total))
            }
        }

        Log.d(TAG, "Items grouped into StatisticItems" + "Result list size: ${resultList.size}")

        return resultList.toList()
    }

    // returns a list of statistics items based on expenses from given time period
    fun getExpensesStatisticsFromPeriod(startDate: LocalDate, endDate: LocalDate): List<StatisticsItem>{

        Log.d(TAG, "Expenses from ${startDate.toString()} - ${endDate.toString()} grouped into StatisticItems")
        return groupItemsIntoStatisticItems(getExpensesFromPeriod(startDate, endDate))
    }

    // returns a list of statistics items based on revenues from given time period
    fun getRevenuesStatisticsFromPeriod(startDate: LocalDate, endDate: LocalDate): List<StatisticsItem>{

        Log.d(TAG, "Revenues from ${startDate.toString()} - ${endDate.toString()} grouped into StatisticItems")
        return groupItemsIntoStatisticItems(getRevenuesFromPeriod(startDate, endDate))
    }

    // returns a list of all expenses
    fun getAllExpenses(): List<ItemData>{

        Log.d(TAG, "All expenses returned." + " Result list size: ${expensesList.size}")
        return expensesList
    }

    // returns a list of all revenues
    fun getAllRevenues(): List<ItemData>{

        Log.d(TAG, "All revenues returned." + " Result list size: ${revenuesList.size}")
        return revenuesList
    }

}