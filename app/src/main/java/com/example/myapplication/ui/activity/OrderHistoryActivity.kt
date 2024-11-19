package com.example.myapplication.ui.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ui.adapter.OrderAdapter
import com.example.myapplication.R
import com.example.myapplication.service.OrderService


class OrderHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)
        val orderService = OrderService()

        val orders = orderService.getOrderHistory(1) //유저 ID로 해당 유저의 주문 내역을 가지고오기

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewOrders)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = OrderAdapter(orders) { order ->
            //showOrderDetailsDialog(order) // 주문 클릭 시 Dialog 표시
        }

        val backBtn: Button = findViewById(R.id.backButton)
        backBtn.setOnClickListener {
            finish()
        }
    }

}
