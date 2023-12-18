package com.example.aplikacjaandroid.models

import android.content.Context
import com.example.aplikacjaandroid.FileManager
import com.example.aplikacjaandroid.ItemData
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object StatisticsModelServices {

    fun getItemsFromPeriod(startDate: LocalDate, endDate: LocalDate, context: Context): List<ItemData>{

        val fileManager: FileManager = FileManager("expenses.txt")

        val items: List<ItemData> = fileManager.readItemsFromFile(context)

        val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy")

        val resultList: List<ItemData> = items.filter{
            val itemDate = LocalDate.parse(it.date, dateFormat)
            itemDate >= startDate && itemDate <= endDate}

        return resultList
    }

    fun groupItemsIntoStatisticItems(items: List<ItemData>): List<StatisticsItem>{

        //categories model dummy
        val categories = listOf("CAR", "ELECTRICITY", "TRAVEL", "HOME", "CLOTHES")

        val resultList = mutableListOf<StatisticsItem>()

        categories.forEach(){ category ->

            val tempList = items.filter{ it.text == category}
            val total: BigDecimal = tempList.map { it.amount }.reduce { acc, amount -> acc + amount }

            resultList.add( StatisticsItem(category, total.toString()))
        }

        return resultList.toList()

    }
    fun getYearsSince(startDate: LocalDate): List<TimeInterval> {

        val resultList = mutableListOf<TimeInterval>()

        var currentYear = LocalDate.now().year
        val startYear = startDate.year

        while(currentYear >= startYear){

            val startDateTemp = LocalDate.of(currentYear,1,1)
            val endDateTemp = LocalDate.of(currentYear,12,31)

            resultList.add(TimeInterval(currentYear.toString(), startDateTemp, endDateTemp))
            currentYear --

        }
        return resultList
    }



}

data class StatisticsItem(
    val label: String,
    val value: String,
    //val color: Color

)

enum class TimeIntervalsLength {
    Today,
    CurrentWeek,
    Month,
    Year
}

data class TimeInterval (
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate

    )






