package com.example.myapplication.data.store

//메뉴 부모인 StoreEntity -> 서버에서 일반 Store 설정 값과 다르게 해놔서 이렇게함..
data class MenuParentStoreEntity(
    val storeId: Int? = null,
    val businessNumber: String? = null,
    val storeName: String? = null,
    val storeDescription: String? = null,
    val storeImage: String? = null
) {

}