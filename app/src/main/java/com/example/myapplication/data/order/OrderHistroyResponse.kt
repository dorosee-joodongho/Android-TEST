package com.example.myapplication.data.order


data class OrderHistoryResponse(
    val orderList : List<OrderHistoryDto>
)

data class OrderHistoryDto(
    val orderDate: String? = null,
    val orderId: Int? = null,
    val storeImg: String? = null,
    val storeName: String? = null,
    val menuName: List<String> = ArrayList(),
    val totalPrice : Int,
    val estimatedTime: String,
    val waiting: String,
    val crowdLevel: String,
    val items: List<OrderItemV2>
)

data class OrderItemV2(
    val name: String,
    val unitPrice: Int,
    val quantity: Int,
    val totalPrice: Int
)

