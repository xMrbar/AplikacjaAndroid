package com.example.aplikacjaandroid.services

import android.content.Context
import com.example.aplikacjaandroid.data.FileManager
import com.example.aplikacjaandroid.data.ItemData
import com.example.aplikacjaandroid.data.StatisticsItem
import java.math.BigDecimal
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import java.util.Locale

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

        var currentMonth = startDate
        var nowDate = LocalDate.now()
        val locale: Locale = Locale("pl_PL")

        while (currentMonth.isBefore(nowDate) || currentMonth.isEqual(nowDate)) {

        }
            val intervalStart = currentMonth.withDayOfMonth(1)
            val intervalEnd = currentMonth.withDayOfMonth(currentMonth.lengthOfMonth())

            val formattedName = currentMonth.format(DateTimeFormatter.ofPattern("yyyy MMMM", locale))
            val monthInterval = TimeInterval(formattedName, intervalStart, intervalEnd)

            resultList.add(monthInterval)

            currentMonth = currentMonth.plus(1, ChronoUnit.MONTHS)


        return resultList.toList()
    }

    fun getWeekRange(date: LocalDate): TimeInterval{

        val startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        val label = "$startOfWeek - $endOfWeek"

        return TimeInterval(label, startOfWeek, endOfWeek)

    }




}

enum class TimeIntervalsLength(val label: String) {
    Today("Dzisiaj"),
    CurrentWeek("Ten tydzień"),
    Month("Miesiąc"),
    Year("Rok")
}

data class TimeInterval (
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate

    )






