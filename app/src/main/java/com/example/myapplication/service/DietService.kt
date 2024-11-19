package com.example.myapplication.service

import com.example.myapplication.data.Diet
import com.example.myapplication.data.MenuItem
import java.time.LocalDate

class DietService {

    fun addDiet(date: String, dietName: String, menuData: List<Map<String, String>>, callback: (Boolean) -> Unit) {
        // 식단 정보 저장
        callback(true) // 성공 여부 반환
    }

    fun deleteDiet(dietId: Int, callback: (Boolean) -> Unit) {
        // 식단 정보 삭제
        callback(true)
    }

    fun getDietDataForMonth(year: Int, month: Int, callback: (List<Diet>) -> Unit) {
        // 식단 정보 리스트 호출
        callback(sampleDietData)
    }

    fun getDietDataForDay(date: LocalDate, callback: (Diet?) -> Unit) {
        // 특정 날짜의 데이터 필터링
        callback(sampleDietData.find { it.date == date })
    }

    companion object {
        val sampleDietData = listOf(
            Diet(
                id = 1,
                date = LocalDate.of(2024, 11, 1),
                name = "아침",
                menuItems = listOf(
                    MenuItem("밥", 300, 60, 10, 2),
                    MenuItem("국", 100, 10, 5, 1)
                )
            ),
            // 더미 데이터 추가 가능
        )
    }
}