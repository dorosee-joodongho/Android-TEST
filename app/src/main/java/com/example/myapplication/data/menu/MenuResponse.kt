package com.example.myapplication.data.menu

import com.example.myapplication.data.store.MenuParentStoreEntity

data class MenuResponse(
    val list : List<MenuEntity>
)
data class MenuEntity(
    val menuId: Int? = null,

    val storeId: Int? = null,
    val menuName: String? = null,
    val menuImage: String? = null,

    val menuDescription: String? = null,
    val menuPrice: Int? = null,
    val calorie: Int? = null,
    val carbs: Int? = null,
    val protein: Int? = null,
    val fat: Int? = null,
    val isSoldOut: Int? = null
) {


}
