package com.example.myapplication.service

import com.example.myapplication.data.CartItem
import com.example.myapplication.data.Order
import com.example.myapplication.data.OrderItem
import com.example.myapplication.data.order.MenuListItem
import com.example.myapplication.data.order.OrderItemV2
import com.example.myapplication.data.order.PostOrderRequestDto
import com.example.myapplication.network.RetrofitApi

class OrderService(private val retrofitApi: RetrofitApi) {

    suspend fun menuOrder(cartItems : MutableList<CartItem>)  : Boolean{
        val orderRequestItemList : MutableList<MenuListItem> = mutableListOf()
        var totalPrice = 0
        val storeId = cartItems[0].storeId //동일 가게 메뉴 이니 아무거나 가지고 오기

        cartItems.forEach {
            val menuItem = MenuListItem(it.name  , it.price , it.quantity)
            orderRequestItemList.add(menuItem)
            totalPrice += menuItem.price
        }

        val postOrderRequestDto = PostOrderRequestDto(
            orderRequestItemList,
            totalPrice
        )
        //전송 로직
        println("전송 데이터  $postOrderRequestDto")

        retrofitApi.menuOrder(storeId , postOrderRequestDto)
        return true
    }

    fun getOrderById() : Order{
        return Order(
                orderId = 1L,
                date = "2022-10-15 (화)",
                storeName = "파스타",
                menuSummary = "피자 외 3",
                orderImage = "null",
                amount = 23000,
                orderDate = "2022-10-15 12:30:00", // 주문 시간
                estimatedTime = "40분", // 예상시간
                waitingTime = "1분", // 웨이팅 시간
                crowdLevel = "원활", // 혼잡도
                items = listOf(
                    OrderItem("짜장면", 5000, 1, 5000),
                    OrderItem("짬뽕", 6000, 1, 6000),
                    OrderItem("군만두", 2000, 1, 2000)
                ),
                totalPrice = 15000 // 총 금액
            )
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

}