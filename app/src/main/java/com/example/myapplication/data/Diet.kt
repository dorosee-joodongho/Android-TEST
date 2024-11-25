package com.example.myapplication.data

import java.time.LocalDate

data class Diet(
    val date: LocalDate,
    val menuItems: List<MenuItem>
)

data class MenuItem(
    val menuName: String,
    var calorie: Int,
    var carbs: Int,
    var protein: Int,
    var fat: Int
)

data class DietResponse(
    val data: List<Diet>
)
