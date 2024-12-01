package com.example.myapplication.data.order


data class OrderHistoryResponse(
    val code : String,
    val item : List<OrderHistoryDto>
)

data class OrderHistoryDto(
    val orderDate: String? = null,
    val orderId: Int? = null,
    val storeImage: String? = null,
    val storeName: String? = null,
    val menuName: List<String> = ArrayList(),
    val totalPrice : Int
)

