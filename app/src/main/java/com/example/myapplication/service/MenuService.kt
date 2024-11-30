package com.example.myapplication.service

import com.example.myapplication.data.menu.Menu
import com.example.myapplication.data.menu.MenuResponse
import com.example.myapplication.network.RetrofitApi

class MenuService (private val retrofitApi: RetrofitApi){

    suspend fun getTodayBestMenu(): List<Menu> {

        // API에서 MenuResponse 객체 받아오기
        val response: MenuResponse = retrofitApi.getStoreRecommendationMenuList()

        println("호출 결과 " + response)
        // 변환할 메뉴 리스트
        return response.list.map { menuEntity ->
            // MenuEntity를 Menu로 변환
            Menu(
                id = menuEntity.menuId?.toLong() ?: 0L,  // Null일 경우 기본값 0L
                name = menuEntity.menuName ?: "Unknown",  // Null일 경우 기본값 설정
                price = menuEntity.menuPrice ?: 0,  // Null일 경우 기본값 설정
                menuImg = menuEntity.menuImage ?: "",  // Null일 경우 기본값 설정
                storeId = menuEntity.store.storeId?.toLong() ?: 0L  // Null일 경우 기본값 설정
            )
        }
    }

}

