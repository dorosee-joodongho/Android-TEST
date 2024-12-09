package com.example.myapplication.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ui.adapter.CartAdapter
import com.example.myapplication.R
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.service.OrderService
import com.example.myapplication.service.PayService
import com.example.myapplication.ui.manager.CartManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CartActivity : AppCompatActivity() {

    private lateinit var cartAdapter: CartAdapter
    private lateinit var totalAmountTextView: TextView
    private val cartItems = CartManager.getItems()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val recyclerView: RecyclerView = findViewById(R.id.cart_recycler_view)
        totalAmountTextView = findViewById(R.id.total_amount)
        val orderButton: Button = findViewById(R.id.order_button)

        cartAdapter = CartAdapter(cartItems) { updateTotalAmount() }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cartAdapter

        val backButton = findViewById<TextView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
        backButton.text = "←  메뉴 목록"

        orderButton.setOnClickListener {
            val totalAmount = cartItems.sumOf { it.price * it.quantity }

            // 결제 처리 및 주문 처리 비동기로 수행
            CoroutineScope(Dispatchers.Main).launch {
                val payService = PayService()
                val orderService = OrderService(RetrofitClient.instance, this@CartActivity)

                // 결제 처리
                val paymentResult = payService.executePayment(totalAmount)
                if (paymentResult) {
                    // 결제 성공 시, 주문 처리
                    val orderResult = orderService.menuOrder(cartItems )

                    if (orderResult) {
                        // 주문 성공
//                        val intent = Intent(this@CartActivity, OrderHistoryActivity::class.java)
//                        startActivity(intent) // 주문 내역으로 이동
                        CartManager.clearCart() // 장바구니 비우기
                        Toast.makeText(this@CartActivity, "주문이 접수되었습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        // 주문 실패
                        Toast.makeText(this@CartActivity, "주문 실패", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // 결제 실패
                    Toast.makeText(this@CartActivity, "결제 실패:", Toast.LENGTH_SHORT).show()
                }
            }
            CoroutineScope(Dispatchers.Main).launch{
                delay(10000)
                var intent = Intent(this@CartActivity, OrderHistoryActivity::class.java)
                startActivity(intent)
                finish();
            }
        }

        updateTotalAmount()
    }

    private fun updateTotalAmount() {
        val totalAmount = cartItems.sumOf { it.price * it.quantity }
        totalAmountTextView.text = "결제금액: ${totalAmount}원"
    }
}
