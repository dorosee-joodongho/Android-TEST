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
    val calories: Int,
    val carbs: Int,
    val protein: Int,
    val fat: Int
)