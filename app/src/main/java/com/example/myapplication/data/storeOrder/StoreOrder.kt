package com.example.myapplication.data.storeOrder

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class StoreOrderItem(
    val orderId: Long,
    val orderDate: String,
    val totalPrice: Int,
    val status: String
)

@Parcelize
data class StoreOrderDetail(
    val menuList: List<StoreMenuItem>,
    val orderDate: String,
    val consumerName: String,
    val consumerPhoneNumber: String
) : Parcelable

@Parcelize
data class StoreMenuItem(
    val orderDetailId: Long,
    val menuName: String,
    val menuPrice: Int,
    val quantity: Int,
): Parcelable


data class GetStoreOrderResponseDto(
    val orderItemList: List<StoreOrderItem>
)