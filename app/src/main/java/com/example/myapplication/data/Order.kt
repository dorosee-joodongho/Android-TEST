package com.example.myapplication.data

data class Order(
    val date: String,
    val storeName: String,
    val menuSummary: String,
    val amount: Int,

    //대기 시간 관련
    val entryTime: Long, // 입장 시간
    val entryOrder: Long, // 입장 순서
    val congestionLevel: String, // 혼잡도

    val items: List<OrderItem> // 상세 주문 목록
)