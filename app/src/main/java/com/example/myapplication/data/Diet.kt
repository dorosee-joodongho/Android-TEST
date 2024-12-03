package com.example.myapplication.data

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

//data class Diet(
//    val date: LocalDate,
//    val menuItems: List<MenuItem>
//)

data class Diet(
    val date: String,
    val menuItems: List<MenuItem>
) {
    // LocalDate로 변환하는 함수
    fun getDateAsLocalDate(): LocalDate {
        return try {
            // 날짜 형식이 yyyy-MM-dd인 경우
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        } catch (e: DateTimeParseException) {
            // 다른 형식(예: yyyy-MM-dd HH:mm:ss)이 있을 경우 추가 처리
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            LocalDate.parse(date, formatter)
        }
    }
}

data class MenuItem(
    val menuName: String,
    var calorie: Int,
    var carbs: Int,
    var protein: Int,
    var fat: Int
)

data class DietResponse(
    val dietList: List<Diet>
)
