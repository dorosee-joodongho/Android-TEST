package com.example.myapplication.service

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.myapplication.data.CartItem
import com.example.myapplication.data.Order
import com.example.myapplication.data.OrderItem
import com.example.myapplication.data.order.MenuListItem
import com.example.myapplication.data.order.OrderItemV2
import com.example.myapplication.data.order.PostOrderRequestDto
import com.example.myapplication.network.RetrofitApi

class OrderService(private val retrofitApi: RetrofitApi, private val context: Context) {

    suspend fun menuOrder(cartItems: MutableList<CartItem>): Boolean {
        val orderRequestItemList: MutableList<MenuListItem> = mutableListOf()
        var totalPrice = 0
        val storeId = cartItems[0].storeId

        cartItems.forEach {
            val menuItem = MenuListItem(it.name, it.price, it.quantity)
            orderRequestItemList.add(menuItem)
            totalPrice += menuItem.price
        }

        val postOrderRequestDto = PostOrderRequestDto(
            orderRequestItemList,
            totalPrice
        )

        println("전송 데이터: $postOrderRequestDto")

        return try {
            val response = retrofitApi.menuOrder(storeId, postOrderRequestDto)
            println("주문 성공: ${response}")
            println("결제 링크: ${response.next_redirect_mobile_url}")

            response.next_redirect_mobile_url?.let {
                openKakaoPay(it) // 결제 페이지로 이동
            } ?: run {
                println("결제 링크를 찾을 수 없습니다.")
                return false
            }
            true
        } catch (e: Exception) {
            println("주문 중 오류 발생: ${e.message}")
            false
        }
    }

    suspend fun getOrderHistory() : List<Order> {
        try {
            val orderResponseList = retrofitApi.getOrderHistory().orderList
            val orderList : MutableList<Order> = mutableListOf()

            println("주문 내역 : $orderResponseList")

            if (orderResponseList != null) {
                for (order in orderResponseList){
                    val orderInfo = Order(
                        orderId = order.orderId!!.toLong() ,
                        date = order.orderDate.toString(),
                        storeName = order.storeName.toString(),
                        menuSummary = createMenuSummary(order.items),
                        amount = order.items.size,
                        orderImage = order.storeImg!!,
                        orderDate = order.orderDate.toString(),
                        estimatedTime = order.estimatedTime,
                        waitingTime = order.waiting,
                        crowdLevel = order.crowdLevel,
                        items = createOrderItems(order.items),
                        totalPrice = order.totalPrice.toLong()
                    )
                    orderList.add(orderInfo)
                }
                return orderList
            }
            return emptyList()
        } catch (e: Exception) {
            println("주문 내역 가져오는 중 오류 발생 : ${e.message}")
            return emptyList()
        }
    }

    private fun createMenuSummary(orderItem : List<OrderItemV2>) : String =
        orderItem[0].name + " 외 "+ orderItem.size

    private fun createOrderItems(orderItem : List<OrderItemV2>) : List<OrderItem>{
        val orderList : MutableList<OrderItem> = mutableListOf()

        orderItem.forEach {
            val orderItem = OrderItem(it.name , it.unitPrice , it.quantity , it.totalPrice)
            orderList.add(orderItem)
        }

        return orderList
    }

    private fun openKakaoPay(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            // 카카오톡 앱을 먼저 열어보려 시도
            intent.setPackage("com.kakao.talk")
            context.startActivity(intent) // Context를 통해 startActivity 호출
        } catch (e: Exception) {
            // 카카오톡이 설치되어 있지 않으면 기본 브라우저로 열기
            intent.setPackage(null)
            context.startActivity(intent) // Context를 통해 startActivity 호출
        }
    }

}