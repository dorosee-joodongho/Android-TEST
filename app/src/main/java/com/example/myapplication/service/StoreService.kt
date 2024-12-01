package com.example.myapplication.service

import com.example.myapplication.data.GetStoreMenuListResponseDto
import com.example.myapplication.data.menu.Menu
import com.example.myapplication.data.store.Store
import com.example.myapplication.data.store.StoreEntity
import com.example.myapplication.network.RetrofitApi

class StoreService(private val retrofitApi: RetrofitApi) {

    suspend fun getNearbyStores(): List<Store> {
        // API에서 MenuResponse 객체 받아오기
        val response: List<StoreEntity> = retrofitApi.getStoreList().list
        println("가게 목록 호출 : $response")
        val storeMenuList : MutableList<Store> = mutableListOf()

        response.forEach {
            val getStore = Store(
                it.storeId!!.toString(),
                it.storeName!!,
                "인천광역시 미추홀구",
                it.state!!,
                it.storeImage!!
            )
            storeMenuList.add(getStore)
        }

        // 랜덤으로 6개의 가게를 선택
        return storeMenuList.shuffled().take(6)
    }

    suspend fun getStoreMenuList(storeId: Long): List<Menu> {
        val response: GetStoreMenuListResponseDto = retrofitApi.getStoreMenuList()
        val menuList : MutableList<Menu> = mutableListOf()
        val menuResponseList = response.list

        for (menuDetail in menuResponseList) {
            val menu = Menu(menuDetail.menuId!! , menuDetail.menuName , menuDetail.menuPrice , menuDetail.menuImage!! , storeId)
            menuList.add(menu)
        }

        return menuList
    }



}