package com.example.myapplication.service

import com.example.myapplication.data.storeOrder.StoreOrderDetail
import com.example.myapplication.data.storeOrder.StoreOrderItem
import com.example.myapplication.network.RetrofitApi

class StoreOrderService(private val retrofitApi: RetrofitApi) {

    // 식당 주문 접수 내역 가져오기
    suspend fun getStoreOrderList(): List<StoreOrderItem>? {
        try {
            val response = retrofitApi.storeOrder()
            println("주문 목록 $response")
            return response.orderItemList
        } catch (e: Exception) {
            println("주문 목록 가져오는 중 오류 발생: ${e.message}")
            return null
        }
    }

    // 식당 주문 상세 가져오기
    suspend fun getOrderDetail(orderId: Long): StoreOrderDetail? {
        try {
            val response = retrofitApi.orderDetail(orderId)
            println("주문 상세 내역 $response")
            return response
        } catch (e: Exception) {
            println("주문 상세 내역 가져오는 중 오류 발생: ${e.message}")
            return null
        }
    }

    // 주문 취소하기
    suspend fun orderDelete(orderId: Long): Boolean {
        try {
            val response = retrofitApi.orderDelete(orderId)
            println("주문 취소 완료 $response")
            return true
        } catch (e: Exception) {
            println("주문 취소 중 오류 발생: ${e.message}")
            return false
        }
    }
}