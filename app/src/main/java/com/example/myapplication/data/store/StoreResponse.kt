package com.example.myapplication.data.store


data class StoreResponse(
    val list: List<StoreEntity>

)

data class StoreEntity(
    val storeId: Int? = null,
    val storeName: String? = null,
    val storeImage: String? = null,
    val state: String? = null
) {

}