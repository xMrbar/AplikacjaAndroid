package com.example.aplikacjaandroid.data

import androidx.compose.ui.graphics.Color

enum class Category(val id:Int, val label:String, val color: Color) {

    // values are to be changed

    CAR(0,"Samochód", Color(0,0,0)),
    ELECTRICITY(1,"Prąd", Color(0,0,0)),
    INCOME(2,"Przychód",Color(0,0,0)),
    TRAVEL(3,"Podróże",Color(0,0,0)),
    HOME(4,"Dom",Color(0,0,0)),
    CLOTHES(5,"Przychód",Color(0,0,0)),
    OTHER(6,"Inne",Color(0,0,0));

    fun getExpenseCategories(): List<Category>{

        return listOf(CAR, ELECTRICITY, TRAVEL, HOME, CLOTHES, OTHER)
    }
    fun getRevenueCategories(): List<Category>{
        return listOf(INCOME, OTHER)
    }



}