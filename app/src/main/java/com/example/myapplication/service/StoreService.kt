package com.example.myapplication.service

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
        //size가 6보디 크면 6개중에 셔플해서 반환
        if(storeMenuList.size > 6){
            return storeMenuList.shuffled().take(6)
        }
        else if(storeMenuList.size > 0){
            return storeMenuList
        }
        // 랜덤으로 6개의 가게를 선택
        return arrayListOf()
    }

    suspend fun getStoreMenuList(storeId: Long): List<Menu>? {
        try {
            val response = retrofitApi.getStoreMenuList(storeId)
            println("메뉴 목록 : ${response.list}")
            return response.list
        } catch (e: Exception) {
            println("메뉴 목록 가져오는 중 오류 발생 : ${e.message}")
            return null
        }

    }



}