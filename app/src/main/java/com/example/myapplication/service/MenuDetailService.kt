package com.example.myapplication.service

import com.example.myapplication.data.MenuDetail
import com.example.myapplication.data.MenuDetailRequest
import com.example.myapplication.network.RetrofitApi
import com.example.myapplication.utils.TransRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class MenuDetailService(private val retrofitApi: RetrofitApi) {

    // 식단 목록 가져오기
    suspend fun getMenuDetailList(): List<MenuDetail>? {
        try {
            val response = retrofitApi.getStoreMenuList()
            println("메뉴 목록 $response")
            return response
        } catch (e: Exception) {
            println("식단 목록 가져오는 중 오류 발생: ${e.message}")
            return null
        }
    }

    suspend fun saveMenuDetail(menuDetail: MenuDetailRequest): Boolean {
        return try {
            // RequestBody 형식으로 변환
            val menuId = TransRequestBody.prepareStringPart("1")
            val storeId = TransRequestBody.prepareStringPart(menuDetail.storeId.toString())
            val name = TransRequestBody.prepareStringPart(menuDetail.name)
            val description = TransRequestBody.prepareStringPart(menuDetail.description)
            val price = TransRequestBody.prepareStringPart(menuDetail.price.toString())
            val calorie = TransRequestBody.prepareStringPart(menuDetail.calorie.toString())
            val carbs = TransRequestBody.prepareStringPart(menuDetail.carbs.toString())
            val protein = TransRequestBody.prepareStringPart(menuDetail.protein.toString())
            val fat = TransRequestBody.prepareStringPart(menuDetail.fat.toString())
            val isSoldOut = TransRequestBody.prepareStringPart(menuDetail.isSoldOut.toString())

            val menuImg = menuDetail.menuImg?.let { TransRequestBody.prepareFilePart(it) }

            val response = retrofitApi.saveMenu(
                menuId, storeId, name, description, price, calorie, carbs, protein, fat, isSoldOut, menuImg
            )
            println("메뉴 저장 성공: ${response.string()}")
            true
        } catch (e: Exception) {
            println("메뉴 저장 중 오류 발생: ${e.message}")
            false
        }
    }

    // 메뉴 수정
    suspend fun updateMenuDetail(menuDetail: MenuDetail): Boolean? {
        return true
    }

    // 메뉴 삭제
    suspend fun deleteMenuDetail(menuId: Long): Boolean? {
        return true
    }

    companion object {
        val menuDetails = listOf(
            MenuDetail(
                menuId = 1,
                storeId = 1,
                menuName = "김치찌개",
                menuDescription = "매콤하고 맛있는 김치찌개",
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
                menuName = "된장찌개",
                menuDescription = "구수한 된장찌개",
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
