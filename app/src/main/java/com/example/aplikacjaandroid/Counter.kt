package com.example.aplikacjaandroid

import android.content.Context
import com.example.aplikacjaandroid.data.FileManager
import com.example.aplikacjaandroid.model.ItemData
import java.math.BigDecimal
import java.util.Calendar

class Counter(private var context: Context) {
    //private val fileAccountBalanceManager = FileManager("accountBalance.txt")
    private val fileExpensesManager = FileManager("expenses.txt")
    private val fileExpensesPlanManager = FileManager("expensesPlan.txt")
    private val fileRevenuesManager = FileManager("revenues.txt")
    private val fileRevenuesPlanManager = FileManager("revenuesPlan.txt")

    private fun countSumInPlan(items: List<ItemData>): BigDecimal {
        val actualMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
        var sum = BigDecimal("0.00")
        for(item in items) {
            if(item.date.contains("CO MIESIĄC", ignoreCase = true)) {
                sum += item.amount
            } else if (item.date.contains("CO 3 MIESIĄCE", ignoreCase = true) &&
                actualMonth % 3 == 1) {
                sum += item.amount
            } else if (item.date.contains("CO 6 MIESIĘCY", ignoreCase = true) &&
                actualMonth % 6 == 1) {
                sum += item.amount
            } else if (actualMonth % 12 == 1) {
                sum += item.amount
            }
        }
        return sum
    }

    private fun countSumThisMonth(items: List<ItemData>): BigDecimal {
        val actualMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
        val actualYear = Calendar.getInstance().get(Calendar.YEAR)
        var sum = BigDecimal("0.00")
        for(item in items) {
            val parts = item.date.split(".")
            val month = parts[1]
            val year = parts[2]
            if(actualMonth.toString().equals(month) && actualYear.toString().equals(year)) {
                sum += item.amount
            }
        }
        return sum
    }

    private fun countSum(items: List<ItemData>): BigDecimal {
        var sum = BigDecimal("0.00")
        for(item in items) {
            sum += item.amount
        }
        return sum
    }

    fun countRevenuesPlan(): BigDecimal {
        val items : List<ItemData> = fileRevenuesPlanManager.readItemsFromFile(context)
        return countSumInPlan(items)
    }

    fun countExpensesPlan(): BigDecimal {
        val items : List<ItemData> = fileExpensesPlanManager.readItemsFromFile(context)
        return countSumInPlan(items)
    }

    fun countExpensesThisMonth(): BigDecimal {
        val items : List<ItemData> = fileExpensesManager.readItemsFromFile(context)
        return countSumThisMonth(items)
    }

    fun countRevenuesThisMonth(): BigDecimal {
        val items : List<ItemData> = fileRevenuesManager.readItemsFromFile(context)
        return countSumThisMonth(items)
    }

    fun countActualBallance(): BigDecimal {
        val itemsE : List<ItemData> = fileExpensesManager.readItemsFromFile(context)
        val itemsR : List<ItemData> = fileRevenuesManager.readItemsFromFile(context)
        return countSum(itemsR) - countSum(itemsE)
    }

    fun countEstaminatedBallance(): BigDecimal {
        return countActualBallance() -
                countRevenuesThisMonth() + countExpensesThisMonth() +
                countRevenuesPlan() - countExpensesPlan()
    }
}