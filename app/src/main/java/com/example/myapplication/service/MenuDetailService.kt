package com.example.myapplication.service

import com.example.myapplication.data.Diet
import com.example.myapplication.data.MenuDetail
import com.example.myapplication.service.DietService.Companion.sampleDietData

class MenuDetailService {

    // 식단 목록 가져오기
    fun getMenuDetailsList(callback: (List<MenuDetail>?) -> Unit) {
        callback(menuDetails) // 목록 반환
    }

    companion object {
        val menuDetails = listOf(
            MenuDetail(
                id = 1,
                storeId = 1,
                name = "김치찌개",
                description = "매콤하고 맛있는 김치찌개",
                price = 8000,
                menuImg = null,
                calorie = 400,
                carbs = 30,
                protein = 20,
                fat = 15,
                isSoldOut = 0
            ),
            MenuDetail(
                id = 2,
                storeId = 1,
                name = "된장찌개",
                description = "구수한 된장찌개",
                price = 7000,
                menuImg = null,
                calorie = 350,
                carbs = 25,
                protein = 15,
                fat = 10,
                isSoldOut = 0
            )

        )
    }
}
