package com.example.aplikacjaandroid.services

import android.util.Log
import com.example.aplikacjaandroid.model.Category
import com.example.aplikacjaandroid.model.ItemData
import com.example.aplikacjaandroid.model.StatisticsItem
import java.math.BigDecimal
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters


/*
 * Enum represents a type of time interval and assigns a label to it
 */
enum class TimeIntervalType(val label: String) {

    // Hardcoded labels
    Today("Dzisiaj"),
    CurrentWeek("Ten tydzień"),
    Month("Miesiąc"),
    Year("Rok")
}

/*
 * Class represents a period of time between given dates and assigns label to it
 */
data class TimeInterval (
    val label: String,
    val startDate: LocalDate,
    val endDate: LocalDate

)

/*
 * Object that provides functions needed for processing statistics data
 */
object StatisticsServices {

    val dateFormat = DateTimeFormatter.ofPattern("[d][dd].[M][MM].yyyy")
    private final val TAG = "StatisticsServices"

    // returns an earliest date form given list of dates
    fun getEarliestDate(items: List<ItemData>): LocalDate{

        val dateList: List<LocalDate> = items.map{ LocalDate.parse(it.date, dateFormat)}
        val result = dateList.sorted()[0]
        Log.d(TAG, "Earliest date returned: ${result.toString()}")
        return result
        
    }

    // returns a sum of StatisticsItems' values from given list
    fun calculateTotal(list: List<StatisticsItem>): BigDecimal{

        val result = list.map { it.value }.reduce { acc, amount -> acc + amount }
        Log.d(TAG, "Returned total: ${result.toString()}")
        return result
    }

    /*
     * returns a list of TimeIntervals for each year form startDate until now
     * Every TimeInterval's starDate is 01.01 and endDate is 31.12
     * Label examples: "2022", "2023"
     */
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
        Log.d(TAG, "TimeIntervals returned. Type: Year. Result list size: ${resultList.size}")
        return resultList.toList()
    }

    // returns a month's number mapped to polish month name
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

    /*
     * returns a list of TimeIntervals for each month form startDate until now
     * Every TimeInterval's starDate is 01 of given month and endDate is last day of given month
     * Label examples with polish months names : "Luty 2022", " Marzec 2023"
     */
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

        Log.d(TAG, "TimeIntervals returned. Type: Month. Result list size: ${resultList.size}")
        return resultList.toList()
    }

    /*
   * returns a TimeInterval representing a week of the given date
   * Label example: "08.01.2024 - 14.01.2024"
   */
    fun getWeekRange(date: LocalDate): TimeInterval{

        val dateFormat = DateTimeFormatter.ofPattern("[dd].[MM].yyyy")

        val startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        val label = "${startOfWeek.format(dateFormat)} - ${endOfWeek.format(dateFormat)}"

        Log.d(TAG, "TimeInterval returned. Type: CurrentWeek. Label: $label")
        return TimeInterval(label, startOfWeek, endOfWeek)

    }


    // returns Category mapped from id
    fun getEnumFromId(id:Int): Category {

        val tempList = Category.values().filter{it.id == id}
        return if(tempList.isNullOrEmpty())
            Category.OTHER
        else
            tempList[0]

    }

}








