package com.example.aplikacjaandroid

import java.math.BigDecimal

data class ItemData(
    val imageResource: Int,
    val text: String,
    val amount: BigDecimal,
    val date: String
)