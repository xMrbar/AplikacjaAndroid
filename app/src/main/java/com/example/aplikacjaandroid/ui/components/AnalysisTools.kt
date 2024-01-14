package com.example.aplikacjaandroid.ui.components

fun polishMonthToNumber(monthName: String): Int? {
    val monthMap = mapOf(
        "styczeń" to 1,
        "luty" to 2,
        "marzec" to 3,
        "kwiecień" to 4,
        "maj" to 5,
        "czerwiec" to 6,
        "lipiec" to 7,
        "sierpień" to 8,
        "wrzesień" to 9,
        "październik" to 10,
        "listopad" to 11,
        "grudzień" to 12
    )

    return monthMap[monthName.lowercase()]
}
fun numberToMonth(monthNum: Int): String? {
    val monthMap = mapOf(
        1 to "Styczeń",
        2 to "Luty",
        3 to "Marzec",
        4 to "Kwiecień",
        5 to "Maj",
        6 to "Czerwiec",
        7 to "Lipiec",
        8 to "Sierpień",
        9 to "Wrzesień",
        10 to "Październik",
        11 to "Listopad",
        12 to "Grudzień"
    )

    return monthMap[monthNum]
}
