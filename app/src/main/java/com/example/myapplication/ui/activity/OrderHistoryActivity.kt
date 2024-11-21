package com.example.myapplication.ui.activity

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ui.adapter.OrderAdapter
import com.example.myapplication.R
import com.example.myapplication.data.Order
import com.example.myapplication.service.OrderService
import com.example.myapplication.ui.adapter.OrderDetailsAdapter


class OrderHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)
        val orderService = OrderService()

        val orders = orderService.getOrderHistory(1) //유저 ID로 해당 유저의 주문 내역을 가지고오기

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewOrders)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = OrderAdapter(orders) { order ->
            // 주문 클릭 시 다이얼로그 호출
            showOrderDetailsDialog(order)
        }

        val backBtn: Button = findViewById(R.id.backButton)
        backBtn.setOnClickListener {
            finish()
        }
    }

    private fun showOrderDetailsDialog(order: Order) {
        // 다이얼로그 레이아웃 inflate
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_order_details, null)

        val rvOrderDetails: RecyclerView = dialogView.findViewById(R.id.rvOrderDetails)
        val tvTotalPrice: TextView = dialogView.findViewById(R.id.tvTotalPrice)

        // RecyclerView 설정
        rvOrderDetails.layoutManager = LinearLayoutManager(this)
        rvOrderDetails.adapter = OrderDetailsAdapter(order.items)

        // 총 금액 표시
        tvTotalPrice.text = "총 결제 금액: ${order.totalPrice}원"

        // 다이얼로그 생성 및 표시
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        // 다이얼로그를 하단에 배치
        dialog.window?.apply {
            setGravity(Gravity.BOTTOM)  // 다이얼로그를 하단에 배치
            attributes = attributes.apply {
                y = 100  // 추가로 y 값으로 마진을 조정할 수 있음
            }
        }

        dialog.show()
    }

}
