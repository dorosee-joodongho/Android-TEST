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
            return response.dietList
        } catch (e: Exception) {
            println("식단 목록 가져오는 중 오류: ${e.message}")
            return emptyList()
        }
    }
}
