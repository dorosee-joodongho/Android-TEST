package com.example.myapplication.data.menu


data class Menu(
    val id : Long,
    val menuName : String,
    val price : Int,
    val menuImg : String,
    val storeId : Long
)

data class GetStoreListResponseDto(
    val list: List<Menu>
)
