package com.example.myapplication.ui.activity

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ui.adapter.OrderAdapter
import com.example.myapplication.R
import com.example.myapplication.data.Order
import com.example.myapplication.service.OrderService

class OrderHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)
        val orderService = OrderService()

        val orders = orderService.getOrderHistory(1) // 유저 ID로 해당 유저의 주문 내역 가져오기

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

        val orderItemsContainer: LinearLayout = dialogView.findViewById(R.id.orderItemsContainer)
        val tvTotalPrice: TextView = dialogView.findViewById(R.id.tvTotalPrice)

        // 주문 항목 동적으로 추가
        for (item in order.items) {
            val orderItemLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 8, 0, 8)
                }
            }

            // 항목 이름
            val itemName = TextView(this).apply {
                text = item.name
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                gravity = Gravity.CENTER // 가운데 정렬

            }

            // 단가
            val itemPrice = TextView(this).apply {
                text = "${item.unitPrice}원"
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                gravity = Gravity.CENTER
            }

            // 수량
            val itemQuantity = TextView(this).apply {
                text = "${item.quantity}개"
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                gravity = Gravity.CENTER
            }

            // 금액
            val itemTotal = TextView(this).apply {
                text = "${item.unitPrice * item.quantity}원"
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                gravity = Gravity.CENTER // 가운데 정렬
            }

            // 각 TextView를 LinearLayout에 추가
            orderItemLayout.addView(itemName)
            orderItemLayout.addView(itemPrice)
            orderItemLayout.addView(itemQuantity)
            orderItemLayout.addView(itemTotal)

            // 최종적으로 Container에 추가
            orderItemsContainer.addView(orderItemLayout)
        }

        // 총 결제 금액 표시
        tvTotalPrice.text = "총 결제 금액: ${order.totalPrice}원"

        // 다이얼로그 생성 및 표시
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        // 다이얼로그를 하단에 배치
        dialog.window?.apply {
            setGravity(Gravity.BOTTOM)
            attributes = attributes.apply {
                y = 100 // 추가로 y 값으로 마진 조정
            }
        }

        dialog.show()
    }
}
