package com.example.myapplication.data.storeOrder

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class StoreOrderItem(
    val orderId: Int,
    val orderDate: String,
    val totalPrice: Int,
    val status: String
)

@Parcelize
data class StoreOrderDetail(
    val status: String,
    val menuList: List<StoreMenuItem>,
    val totalPayment: Int,
    val orderDate: String,
    val customerName: String,
    val customerPhoneNumber: String
) : Parcelable

@Parcelize
data class StoreMenuItem(
    val menuName: String,
    val menuPrice: Int,
    val quantity: Int,
    val totalAmount: Int
): Parcelable
