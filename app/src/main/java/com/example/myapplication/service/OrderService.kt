package com.example.myapplication.service

import com.example.myapplication.data.Order
import com.example.myapplication.data.OrderItem

class OrderService {
    fun getOrderById() : Order{
        return Order(
                orderId = 1L,
                date = "2022-10-15 (화)",
                storeName = "파스타",
                menuSummary = "피자 외 3",
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

    fun getOrderHistory(userId : Long) : List<Order> {
        return listOf(
            Order(
                orderId = 1L,
                date = "2022-10-15 (화)",
                storeName = "파스타",
                menuSummary = "피자 외 3",
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
            ),
            Order(
                orderId = 1L,
                date = "2022-10-15 (화)",
                storeName = "파스타",
                menuSummary = "피자 외 3",
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
            ),
            Order(
                orderId = 1L,
                date = "2022-10-15 (화)",
                storeName = "파스타",
                menuSummary = "피자 외 3",
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
        )

    }


}