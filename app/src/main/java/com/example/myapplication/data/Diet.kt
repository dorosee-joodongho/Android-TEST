package com.example.myapplication.data

import java.time.LocalDate

data class Diet(
    val id: Int?,
    val date: LocalDate,
    val name: String,
    val menuItems: List<MenuItem>
)

data class MenuItem(
    val name: String,
    var calories: Int,
    var carbs: Int,
    var protein: Int,
    var fat: Int
)