package com.example.myapplication.data

import java.io.File
import java.io.Serializable

data class MenuDetail (
    val menuId: Long?,
    var menuName: String,
    var menuDescription: String,
    var menuPrice : Int,
    var menuImage : String?,
    var calorie: Int,
    var carbs: Int,
    var protein: Int,
    var fat: Int,
    var isSoldOut: Int // 1이 품절
) : Serializable

data class GetStoreMenuListResponseDto(
    val list: List<MenuDetail>
)

data class MenuDetailRequest (
    val menuId: Long?,
    var name: String,
    var description: String,
    var price : Int,
    var menuImg : File?,
    var calorie: Int,
    var carbs: Int,
    var protein: Int,
    var fat: Int,
    var isSoldOut: Int // 1이 품절
) : Serializable

data class GetStoreMenuItem(
    val menuId: Int,
    val menuName: String,
    val menuImage: String,
    val menuDescription: String,
    val menuPrice: Int
)