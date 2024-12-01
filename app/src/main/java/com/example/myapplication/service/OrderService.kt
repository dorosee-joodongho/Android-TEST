package com.example.myapplication.service

import com.example.myapplication.data.CartItem
import com.example.myapplication.data.Order
import com.example.myapplication.data.OrderItem
import com.example.myapplication.data.menu.Menu
import com.example.myapplication.data.order.MenuListItem
import com.example.myapplication.data.order.OrderHistoryDto
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
        val orderResponseList = retrofitApi.getOrderHistory().item

        val orderList : MutableList<Order> = mutableListOf()

        println("주문 History $orderResponseList")

        for (order in orderResponseList){
            val orderInfo = Order(
                orderId = order.orderId!!.toLong() ,
                date = order.orderDate.toString(),
                storeName = order.storeName.toString(),
                menuSummary = createMenuSummary(order),
                amount = createAmount(order),
                orderImage = order.storeImage!!,
                orderDate = order.orderDate.toString(),
                estimatedTime = "10분",
                waitingTime = "10분",
                crowdLevel = "보통",
                items = createOrderItems(order),
                totalPrice = order.totalPrice.toLong()
            )
            orderList.add(orderInfo)
        }
        return orderList
    }

    private fun createMenuSummary(order : OrderHistoryDto) : String =
        order.menuName[0] + " 외 "+ order.menuName.size


    private fun createAmount(order : OrderHistoryDto) : Int =
        order.menuName.size

    private fun createOrderItems(order: OrderHistoryDto) : List<OrderItem>{
        val orderList : MutableList<OrderItem> = mutableListOf()

        order.menuName.forEach {
            val orderItem = OrderItem(it , 5000 , 1 , order.totalPrice)
            orderList.add(orderItem)
        }

        return orderList
    }

}