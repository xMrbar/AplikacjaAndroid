package com.example.aplikacjaandroid.data

import android.annotation.SuppressLint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.example.aplikacjaandroid.R
import kotlin.math.absoluteValue

enum class Category(val id:Int, val label:String, val color: Color) {

    // values must look like that because otherwise colors stop working in pie chart

    @SuppressLint("ResourceAsColor")
    CAR(0, "Samochód", Color(144, 170, 222, 255)),
    @SuppressLint("ResourceAsColor")
    ELECTRICITY(1, "Prąd", Color(107, 126, 165, 255)),
    @SuppressLint("ResourceAsColor")
    INCOME(2, "Przychód", Color(168, 144, 222, 255)),
    @SuppressLint("ResourceAsColor")
    TRAVEL(3, "Podróże", Color(145, 146, 222, 255)),
    @SuppressLint("ResourceAsColor")
    HOME(4, "Dom", Color(126, 188, 222, 255)),
    @SuppressLint("ResourceAsColor")
    CLOTHES(5, "Ubrania", Color(194, 144, 222, 255)),
    @SuppressLint("ResourceAsColor")
    OTHER(6, "Inne", Color(94, 140, 165, 255));

}