package com.example.myapplication.data

data class CartItem(
    val storeId : Long,
    val name: String, //아이템 이름
    val price: Int, //가격
    var quantity: Int //재고
) {

}