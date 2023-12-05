package com.example.aplikacjaandroid

import java.math.BigDecimal

data class ItemData(
    val id: Int,
    val imageResource: Int,
    val text: String,
    val amount: BigDecimal,
    val date: String
)