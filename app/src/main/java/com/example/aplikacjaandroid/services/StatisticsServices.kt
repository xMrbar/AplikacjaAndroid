package com.example.aplikacjaandroid.services

import android.content.Context
import com.example.aplikacjaandroid.data.FileManager
import com.example.aplikacjaandroid.data.ItemData
import com.example.aplikacjaandroid.data.StatisticsItem
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object StatisticsServices {

    val dateFormat = DateTimeFormatter.ofPattern("[d][dd].[M][MM].yyyy")

    fun getEarliestDate(items: List<ItemData>): LocalDate{

        val dateList: List<LocalDate> = items.map{ LocalDate.parse(it.date, dateFormat)}
        return dateList.sorted()[0]
        
    }

    fun calculateTotal(list: List<StatisticsItem>): BigDecimal{

        return list.map { it.value }.reduce { acc, amount -> acc + amount }
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
        return resultList.toList()
    }

    fun getMonthsSince(startDate: LocalDate): List<TimeInterval>{

        val resultList = mutableListOf<TimeInterval>()





        return resultList.toList()
    }



}

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






