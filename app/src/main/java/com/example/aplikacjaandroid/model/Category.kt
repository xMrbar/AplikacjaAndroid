package com.example.aplikacjaandroid.model

import androidx.compose.ui.graphics.Color

enum class Category(val id:Int, val label:String, val color: Color) {

    // values must look like that because otherwise colors stop working correctly in pie chart,
    // and every category has the same blue color

    CAR(0, "Samochód", Color(144, 170, 222, 255)),

    ELECTRICITY(1, "Prąd", Color(107, 126, 165, 255)),

    SALARY(2, "Pensja", Color(168, 144, 222, 255)),

    TRAVEL(3, "Podróże", Color(145, 146, 222, 255)),

    HOME(4, "Dom", Color(126, 188, 222, 255)),

    CLOTHES(5, "Ubrania", Color(194, 144, 222, 255)),

    OTHER(6, "Inne", Color(94, 140, 165, 255)),

    RENTAL_INCOME(7, "Dochód z najmu", Color(57, 107, 134, 255)),

    GIFT(8, "Prezent", Color(57, 107, 134, 255)),

    INVESTMENT_INCOME(9, "Dochód z inwestycji", Color(84, 57, 134, 255)),

    SALE_OF_PROPERTY(10, "Sprzedaż majątku", Color(104, 57, 134, 255)),

    ;
}

object CategoryHelper{

    fun getRevenueCategories(): List<Category>{

        return listOf(Category.SALARY, Category.RENTAL_INCOME, Category.GIFT, Category.INVESTMENT_INCOME,
            Category.SALE_OF_PROPERTY, Category.OTHER)
    }

    fun getExpenseCategories(): List<Category>{

        return listOf(Category.CAR, Category.ELECTRICITY, Category.TRAVEL, Category.HOME,
            Category.CLOTHES, Category.OTHER)
    }


}
