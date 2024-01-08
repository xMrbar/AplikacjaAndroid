package com.example.aplikacjaandroid.services

import com.example.aplikacjaandroid.model.Category
import com.example.aplikacjaandroid.model.ItemData
import com.example.aplikacjaandroid.model.StatisticsItem
import java.math.BigDecimal
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

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

    private fun getFormattedMonthName( month: Int): String {
        return when(month){
            1 -> "Styczeń"
            2 -> "Luty"
            3 -> "Marzec"
            4 -> "Kwiecień"
            5 -> "Maj"
            6 -> "Czerwiec"
            7 -> "Lipiec"
            8 -> "Sierpień"
            9 -> "Wrzesień"
            10 -> "Październik"
            11 -> "Listopad"
            12 -> "Grudzeiń"
            else -> ""
        }
    }

    fun getMonthsSince(startDate: LocalDate): List<TimeInterval>{

        val resultList = mutableListOf<TimeInterval>()

        var currentMonth = startDate
        var nowDate = LocalDate.now()

        while (currentMonth.isBefore(nowDate) || currentMonth.month == nowDate.month) {


            val intervalStart = currentMonth.withDayOfMonth(1)
            val intervalEnd = currentMonth.withDayOfMonth(currentMonth.lengthOfMonth())

            val formattedName =
                getFormattedMonthName(currentMonth.monthValue) + " " +
                currentMonth.format(DateTimeFormatter.ofPattern("yyyy"))
            val monthInterval = TimeInterval(formattedName, intervalStart, intervalEnd)

            resultList.add(monthInterval)

            currentMonth = currentMonth.plus(1, ChronoUnit.MONTHS)
        }


        return resultList.toList()
    }

    fun getWeekRange(date: LocalDate): TimeInterval{

        val dateFormat = DateTimeFormatter.ofPattern("[dd].[MM].yyyy")

        val startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        val label = "${startOfWeek.format(dateFormat)} - ${endOfWeek.format(dateFormat)}"

        return TimeInterval(label, startOfWeek, endOfWeek)

    }

    fun getEnumFromId(id:Int): Category {

        val tempList = Category.values().filter{it.id == id}
        if(tempList.isNullOrEmpty())
            return Category.OTHER
        else
            return tempList[0]

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






