package com.example.aplikacjaandroid.models

class StatisticsModel {





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




