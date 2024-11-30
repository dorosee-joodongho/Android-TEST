package com.example.myapplication.data.store

data class Store(
    val storeId: String,        // 가게 ID
    val storeName: String,      // 가게 이름
    val location: String,       // 가게 위치
    val storeStatus: String,    // 가게 혼잡도 (예: "매우 혼잡", "보통", "여유 있음")
    val storeLogoImg: String  // 가게 이미지 (URL)
)
