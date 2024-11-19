package com.example.myapplication.service

import com.example.myapplication.data.Order
import com.example.myapplication.data.OrderItem

class OrderService {

    fun getOrderHistory(userId : Long) : List<Order> {
        return listOf(
            Order(
                "2022-10-15 (화)",
                "중국집",
                "짜장면 외 3",
                23000,
                35,
                3,
                "보통",
                listOf(
                    OrderItem("짜장면", 5000, 1),
                    OrderItem("짬뽕", 6000, 1),
                    OrderItem("탕수육", 12000, 1)
                )
            ),
            Order(
                "2022-10-15 (화)",
                "파스타",
                "피자 외 3",
                23000,
                40,
                1,
                "원활",
                listOf(
                    OrderItem("파스타", 5000, 1),
                    OrderItem("피자", 6000, 1),
                    OrderItem("레전드 음식", 12000, 1)
                )
            ),
            Order(
                "2022-10-15 (화)",
                "스시집",
                "TEST 외 3",
                23000,
                60,
                10,
                "혼잡",
                listOf(
                    OrderItem("냉 소바", 5000, 1),
                    OrderItem("스시", 6000, 1),
                    OrderItem("닭강정", 12000, 1)
                )
            )
        )

    }


}