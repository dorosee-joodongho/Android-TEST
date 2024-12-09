package com.example.myapplication.data.order

data class PostOrderRequestDto(
    var list: List<MenuListItem>? = null,
    val totalPrice: Int? = null
)
data class MenuListItem(
    val menuName : String,
    val price : Int,
    val quantity : Int
)