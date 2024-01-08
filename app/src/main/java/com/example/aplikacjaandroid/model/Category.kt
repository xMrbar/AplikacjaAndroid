package com.example.aplikacjaandroid.model

import androidx.compose.ui.graphics.Color

enum class Category(val id:Int, val label:String, val color: Color) {

    // values must look like that because otherwise colors stop working correctly in pie chart,
    // and every category has the same blue color

    CAR(0, "Samochód", Color(144, 170, 222, 255)),

    ELECTRICITY(1, "Prąd", Color(107, 126, 165, 255)),

    INCOME(2, "Przychód", Color(168, 144, 222, 255)),

    TRAVEL(3, "Podróże", Color(145, 146, 222, 255)),

    HOME(4, "Dom", Color(126, 188, 222, 255)),

    CLOTHES(5, "Ubrania", Color(194, 144, 222, 255)),

    OTHER(6, "Inne", Color(94, 140, 165, 255));

}