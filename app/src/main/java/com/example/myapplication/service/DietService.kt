package com.example.myapplication.service

import com.example.myapplication.data.Diet
import com.example.myapplication.data.MenuItem
import com.example.myapplication.network.RetrofitApi
import java.time.LocalDate

class DietService(private val retrofitApi: RetrofitApi) {

    // 식단 목록 가져오기
    suspend fun getDietList(): List<Diet> {
        try {
            val response = retrofitApi.getDietList()
            println("식단 목록 : ${response}")
            return response.data ?: emptyList()
        } catch (e: Exception) {
            println("식단 목록 가져오는 중 오류: ${e.message}")
            throw e
        }
    }

    companion object {
        val sampleDietData = listOf(
            Diet(
//                id = 1,
                date = LocalDate.of(2024, 11, 1),
//                dietName = "아침",
                menuItems = listOf(
                    MenuItem("밥", 300, 60, 10, 2),
                    MenuItem("국", 100, 10, 5, 1)
                )
            ),
            Diet(
//                id = 2,
                date = LocalDate.of(2024, 11, 2),
//                dietName = "점심",
                menuItems = listOf(
                    MenuItem("밥", 350, 70, 12, 3),
                    MenuItem("국", 120, 15, 8, 2)
                )
            ),
            Diet(
//                id = 3,
                date = LocalDate.of(2024, 11, 2),
//                dietName = "저녁",
                menuItems = listOf(
                    MenuItem("면", 500, 150, 20, 10),
                    MenuItem("국", 150, 20, 5, 3)
                )
            ),
            Diet(
//                id = 4,
                date = LocalDate.of(2024, 10, 1),
//                dietName = "저녁",
                menuItems = listOf(
                    MenuItem("밥", 300, 60, 10, 2),
                    MenuItem("국", 100, 10, 5, 1)
                )
            )
        ) // sampleDietData
    } // object
}
