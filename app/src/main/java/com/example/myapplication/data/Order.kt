package com.example.myapplication.data

data class Order(
    val orderId : Long,
    val date: String,
    val storeName: String,
    val menuSummary: String,
    val amount: Int,

    //대기 시간 관련
    val orderDate : String, //주문 시간
    val estimatedTime : String, //예상시간
    val waitingTime : String , //웨이팅 시간
    val crowdLevel : String,


    val items: List<OrderItem>, // 상세 주문 목록
    val totalPrice : Long
)