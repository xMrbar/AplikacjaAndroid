package com.example.aplikacjaandroid

import android.content.Context
import java.math.BigDecimal
import java.util.Calendar

class Counter(private var context: Context) {
    private val fileAccountBalanceManager = FileManager("accountBalance.txt")
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

    fun countRevenuesPlan(): BigDecimal {
        val itemsRevenuesPlan : List<ItemData> = fileRevenuesPlanManager.readItemsFromFile(context)
        return countSumInPlan(itemsRevenuesPlan)
    }

    fun countExpensesPlan(): BigDecimal {
        val itemsRevenuesPlan : List<ItemData> = fileExpensesPlanManager.readItemsFromFile(context)
        return countSumInPlan(itemsRevenuesPlan)
    }
}