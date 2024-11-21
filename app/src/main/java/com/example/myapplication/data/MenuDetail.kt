package com.example.myapplication.data

import android.net.Uri
import java.io.Serializable

data class MenuDetail (
    val id: Long?,
    val storeId : Long,
    var name: String,
    var description: String,
    var price : Int,
    var menuImg : Uri?,
    var calorie: Int,
    var carbs: Int,
    var protein: Int,
    var fat: Int,
    var isSoldOut: Int // 1이 품절
) : Serializable