package com.example.myapplication.data

import android.net.Uri
import java.io.File
import java.io.Serializable

data class MenuDetail (
    val menuId: Long?,
    val storeId : Long,
    var name: String,
    var description: String,
    var price : Int,
    var menuImg : String?,
    var calorie: Int,
    var carbs: Int,
    var protein: Int,
    var fat: Int,
    var isSoldOut: Int // 1이 품절
) : Serializable

data class MenuDetailRequest (
    val menuId: Long?,
    val storeId : Long,
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

data class GetStoreMenuListResponseDto(
    val list: List<GetStoreMenuItem>
)

data class GetStoreMenuItem(
    val menuId: Int,
    val menuName: String,
    val menuImage: String,
    val menuDescription: String,
    val menuPrice: Int
)