package com.example.myapplication.service

import com.example.myapplication.data.MenuDetail
import com.example.myapplication.network.APICall
import com.example.myapplication.network.APIService
import com.example.myapplication.network.RetrofitClient

class MenuDetailService {
//    private val apiService = RetrofitClient.instance.create(APIService::class.java)
//    private val apiCall = APICall(apiService)

    // 식단 목록 가져오기
    fun getMenuDetailList(): List<MenuDetail> {
        return menuDetails
    }

    suspend fun saveMenuDetail(menuDetail: MenuDetail): Boolean? {
        return true
//        return try {
//            val response : Boolean? = apiCall.apiCall(
//                method = "POST",
//                apiEndpoint = "/menu/save",
//                requestBody = menuDetail
//            )
//            response == true
//        } catch (e: Exception) {
//            println("오류 발생: ${e.message}")
//            null // 저장 실패
//        }
    }

    // 메뉴 수정
    suspend fun updateMenuDetail(menuDetail: MenuDetail): Boolean? {
        return try {
//            val response: Boolean? = apiCall.apiCall(
//                method = "PUT",
//                apiEndpoint = "/menu/update/${menuDetail.menuId}", // menuId로 특정 메뉴 식별
//                requestBody = menuDetail
//            )
//            response == true
            true
        } catch (e: Exception) {
            println("오류 발생: ${e.message}")
            null // 수정 실패
        }
    }

    // 메뉴 삭제
    suspend fun deleteMenuDetail(menuId: Long): Boolean? {
        return try {
//            val response: Boolean? = apiCall.apiCall(
//                method = "DELETE",
//                apiEndpoint = "/menu/delete/$menuId" // menuId로 특정 메뉴 식별
//            )
//            response == true
            true
        } catch (e: Exception) {
            println("오류 발생: ${e.message}")
            null // 삭제 실패
        }
    }

    companion object {
        val menuDetails = listOf(
            MenuDetail(
                menuId = 1,
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
                menuId = 2,
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
