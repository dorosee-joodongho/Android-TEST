package com.example.myapplication.data

import java.time.LocalDate

data class Diet(
    val id: Long?,
    val date: LocalDate,
    val name: String,
    val menuItems: List<MenuItem>
)

data class MenuItem(
    val name: String,
    var calorie: Int,
    var carbs: Int,
    var protein: Int,
    var fat: Int
)