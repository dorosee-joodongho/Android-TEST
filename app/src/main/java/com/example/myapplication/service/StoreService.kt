package com.example.myapplication.service

import com.example.myapplication.data.Menu
import com.example.myapplication.data.Store
import kotlin.random.Random

class StoreService {

    fun getNearbyStores(): List<Store> {
        val sampleStores = listOf(
            Store("1", "Pizza Place", "서울 강남구", "매우 혼잡", "https://marketplace.canva.com/EAGFLnRi-2s/1/0/1600w/canva-%EB%B8%8C%EB%9D%BC%EC%9A%B4%EC%83%89-%EC%A0%A0%EC%8A%A4%ED%83%80%EC%9D%BC%EC%9D%98-%ED%95%9C%EA%B5%AD-%EC%A0%84%ED%86%B5-%EB%94%94%EC%A0%80%ED%8A%B8-%EA%B0%80%EA%B2%8C-%EB%A1%9C%EA%B3%A0-9Yl6SORDlYQ.jpg"),
            Store("2", "Sushi Bar", "서울 종로구", "보통", "https://marketplace.canva.com/EAGFLnRi-2s/1/0/1600w/canva-%EB%B8%8C%EB%9D%BC%EC%9A%B4%EC%83%89-%EC%A0%A0%EC%8A%A4%ED%83%80%EC%9D%BC%EC%9D%98-%ED%95%9C%EA%B5%AD-%EC%A0%84%ED%86%B5-%EB%94%94%EC%A0%80%ED%8A%B8-%EA%B0%80%EA%B2%8C-%EB%A1%9C%EA%B3%A0-9Yl6SORDlYQ.jpg"),
            Store("3", "Cafe Mocha", "서울 마포구", "여유 있음", "https://marketplace.canva.com/EAGFLnRi-2s/1/0/1600w/canva-%EB%B8%8C%EB%9D%BC%EC%9A%B4%EC%83%89-%EC%A0%A0%EC%8A%A4%ED%83%80%EC%9D%BC%EC%9D%98-%ED%95%9C%EA%B5%AD-%EC%A0%84%ED%86%B5-%EB%94%94%EC%A0%80%ED%8A%B8-%EA%B0%80%EA%B2%8C-%EB%A1%9C%EA%B3%A0-9Yl6SORDlYQ.jpg"),
            Store("4", "Burger King", "서울 강동구", "매우 혼잡", "https://marketplace.canva.com/EAGFLnRi-2s/1/0/1600w/canva-%EB%B8%8C%EB%9D%BC%EC%9A%B4%EC%83%89-%EC%A0%A0%EC%8A%A4%ED%83%80%EC%9D%BC%EC%9D%98-%ED%95%9C%EA%B5%AD-%EC%A0%84%ED%86%B5-%EB%94%94%EC%A0%80%ED%8A%B8-%EA%B0%80%EA%B2%8C-%EB%A1%9C%EA%B3%A0-9Yl6SORDlYQ.jpg"),
            Store("5", "Boba Tea", "부산 해운대", "보통", "https://marketplace.canva.com/EAGFLnRi-2s/1/0/1600w/canva-%EB%B8%8C%EB%9D%BC%EC%9A%B4%EC%83%89-%EC%A0%A0%EC%8A%A4%ED%83%80%EC%9D%BC%EC%9D%98-%ED%95%9C%EA%B5%AD-%EC%A0%84%ED%86%B5-%EB%94%94%EC%A0%80%ED%8A%B8-%EA%B0%80%EA%B2%8C-%EB%A1%9C%EA%B3%A0-9Yl6SORDlYQ.jpg"),
            Store("6", "Noodle Shop", "서울 서초구", "여유 있음", "https://marketplace.canva.com/EAGFLnRi-2s/1/0/1600w/canva-%EB%B8%8C%EB%9D%BC%EC%9A%B4%EC%83%89-%EC%A0%A0%EC%8A%A4%ED%83%80%EC%9D%BC%EC%9D%98-%ED%95%9C%EA%B5%AD-%EC%A0%84%ED%86%B5-%EB%94%94%EC%A0%80%ED%8A%B8-%EA%B0%80%EA%B2%8C-%EB%A1%9C%EA%B3%A0-9Yl6SORDlYQ.jpg"),
            Store("7", "BBQ Chicken", "서울 송파구", "매우 혼잡", "https://marketplace.canva.com/EAGFLnRi-2s/1/0/1600w/canva-%EB%B8%8C%EB%9D%BC%EC%9A%B4%EC%83%89-%EC%A0%A0%EC%8A%A4%ED%83%80%EC%9D%BC%EC%9D%98-%ED%95%9C%EA%B5%AD-%EC%A0%84%ED%86%B5-%EB%94%94%EC%A0%80%ED%8A%B8-%EA%B0%80%EA%B2%8C-%EB%A1%9C%EA%B3%A0-9Yl6SORDlYQ.jpg"),
            Store("8", "Steak House", "서울 강북구", "보통", "https://marketplace.canva.com/EAGFLnRi-2s/1/0/1600w/canva-%EB%B8%8C%EB%9D%BC%EC%9A%B4%EC%83%89-%EC%A0%A0%EC%8A%A4%ED%83%80%EC%9D%BC%EC%9D%98-%ED%95%9C%EA%B5%AD-%EC%A0%84%ED%86%B5-%EB%94%94%EC%A0%80%ED%8A%B8-%EA%B0%80%EA%B2%8C-%EB%A1%9C%EA%B3%A0-9Yl6SORDlYQ.jpg"),
            Store("9", "Ice Cream Shop", "부산 수영구", "여유 있음", "https://marketplace.canva.com/EAGFLnRi-2s/1/0/1600w/canva-%EB%B8%8C%EB%9D%BC%EC%9A%B4%EC%83%89-%EC%A0%A0%EC%8A%A4%ED%83%80%EC%9D%BC%EC%9D%98-%ED%95%9C%EA%B5%AD-%EC%A0%84%ED%86%B5-%EB%94%94%EC%A0%80%ED%8A%B8-%EA%B0%80%EA%B2%8C-%EB%A1%9C%EA%B3%A0-9Yl6SORDlYQ.jpg"),
            Store("10", "Vegan Cafe", "서울 마포구", "매우 혼잡", "https://marketplace.canva.com/EAGFLnRi-2s/1/0/1600w/canva-%EB%B8%8C%EB%9D%BC%EC%9A%B4%EC%83%89-%EC%A0%A0%EC%8A%A4%ED%83%80%EC%9D%BC%EC%9D%98-%ED%95%9C%EA%B5%AD-%EC%A0%84%ED%86%B5-%EB%94%94%EC%A0%80%ED%8A%B8-%EA%B0%80%EA%B2%8C-%EB%A1%9C%EA%B3%A0-9Yl6SORDlYQ.jpg")
        )

        // 랜덤으로 6개의 가게를 선택
        return sampleStores.shuffled().take(6)
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
            val randomPrice = "${Random.nextInt(5000, 20000)}원" // 랜덤 가격 생성 (5000원 ~ 20000원)
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