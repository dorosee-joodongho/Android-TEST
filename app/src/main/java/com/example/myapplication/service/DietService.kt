package com.example.myapplication.service

import com.example.myapplication.data.Diet
import com.example.myapplication.data.MenuItem
import java.time.LocalDate

class DietService {

    // 식단 추가 : 구현 X
    fun saveDiet(diet: Diet, callback: (Diet?) -> Unit) {
        // 식단 정보 저장
        callback(diet) // 저장 객체 반환
    }
    // 식단 수정 : 구현 X
    fun updateDiet(diet: Diet, callback: (Diet?) -> Unit) {
        // 식단 정보 수정
        callback(diet)
    }
    // 식단 삭제 : 구현 X
    fun deleteDiet(dietId: Long, callback: (Boolean) -> Unit) {
        // 식단 정보 삭제
        callback(true)
    }

    // 식단 목록 가져오기
    fun getDietList(callback: (List<Diet>?) -> Unit) {
        callback(sampleDietData) // 목록 반환
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
            Diet(
                id = 2,
                date = LocalDate.of(2024, 11, 2),
                name = "점심",
                menuItems = listOf(
                    MenuItem("밥", 350, 70, 12, 3),
                    MenuItem("국", 120, 15, 8, 2)
                )
            ),
            Diet(
                id = 3,
                date = LocalDate.of(2024, 11, 2),
                name = "저녁",
                menuItems = listOf(
                    MenuItem("면", 500, 150, 20, 10),
                    MenuItem("국", 150, 20, 5, 3)
                )
            ),
            Diet(
                id = 4,
                date = LocalDate.of(2024, 10, 1),
                name = "저녁",
                menuItems = listOf(
                    MenuItem("밥", 300, 60, 10, 2),
                    MenuItem("국", 100, 10, 5, 1)
                )
            )
        ) // sampleDietData
    } // object
}
