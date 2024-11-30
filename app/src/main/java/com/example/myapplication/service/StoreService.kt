package com.example.myapplication.service

import com.example.myapplication.data.menu.Menu
import com.example.myapplication.data.store.Store
import com.example.myapplication.data.store.StoreEntity
import com.example.myapplication.network.RetrofitApi
import kotlin.random.Random

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

    fun getStoreMenuList(storeId: Long): List<Menu> {
        // 메뉴 이름 리스트 (임시로 사용할 메뉴 이름들)
        val menuNames = listOf("Pizza", "Burger", "Pasta", "Sushi", "Steak", "Salad", "Tacos", "Sandwich", "Fries", "Soup")

        // 이미지 URL 리스트 (임시로 사용할 이미지 URL들)
        val menuImages = listOf(
            "https://img.freepik.com/free-vector/flat-design-korean-food-illustration_23-2149285208.jpg",
            "https://img.freepik.com/free-vector/flat-design-korean-food-illustration_23-2149285208.jpg",
            "https://img.freepik.com/free-vector/flat-design-korean-food-illustration_23-2149285208.jpg",
            "https://img.freepik.com/free-vector/flat-design-korean-food-illustration_23-2149285208.jpg",
            "https://img.freepik.com/free-vector/flat-design-korean-food-illustration_23-2149285208.jpg",
            "https://img.freepik.com/free-vector/flat-design-korean-food-illustration_23-2149285208.jpg",
            "https://img.freepik.com/free-vector/flat-design-korean-food-illustration_23-2149285208.jpg",
            "https://img.freepik.com/free-vector/flat-design-korean-food-illustration_23-2149285208.jpg",
            "https://img.freepik.com/free-vector/flat-design-korean-food-illustration_23-2149285208.jpg",
            "https://img.freepik.com/free-vector/flat-design-korean-food-illustration_23-2149285208.jpg"
        )

        // 메뉴 리스트를 랜덤하게 9개 생성
        val menuList = mutableListOf<Menu>()
        for (i in 1..9) {
            val randomIndex = Random.nextInt(menuNames.size) // 랜덤한 메뉴 이름과 이미지 인덱스
            val randomPrice = Random.nextInt(5000, 20000) // 랜덤 가격 생성 (5000원 ~ 20000원)
            val menu = Menu(
                id = 1,
                name = menuNames[randomIndex],
                price = randomPrice,
                menuImg = menuImages[randomIndex],
                storeId = storeId
            )
            menuList.add(menu)
        }
        return menuList
    }



}