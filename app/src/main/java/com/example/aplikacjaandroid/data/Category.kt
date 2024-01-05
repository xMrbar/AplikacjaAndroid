package com.example.aplikacjaandroid.data

import androidx.compose.ui.graphics.Color

enum class Category(val id:Int, val label:String, val color: Color) {

    // values are to be changed

    CAR(0,"Samochód", Color(255,0,0)),
    ELECTRICITY(1,"Prąd", Color(0,255,0)),
    INCOME(2,"Przychód",Color(0,0,255)),
    TRAVEL(3,"Podróże",Color(255,255,0)),
    HOME(4,"Dom",Color(0,255,255)),
    CLOTHES(5,"Ubrania",Color(255,0,255)),
    OTHER(6,"Inne",Color(0,0,0));

}