package com.example.myapplication.ui.activity

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
import com.example.myapplication.service.PayService
import com.example.myapplication.ui.manager.CartManager

class CartActivity : AppCompatActivity() {

    private lateinit var cartAdapter: CartAdapter
    private lateinit var totalAmountTextView: TextView

    private val cartItems = CartManager.getItems()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val recyclerView: RecyclerView = findViewById(R.id.cart_recycler_view)
        totalAmountTextView = findViewById(R.id.total_amount)
        val orderButton: Button = findViewById(R.id.order_button)

        cartAdapter = CartAdapter(cartItems) { updateTotalAmount() }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cartAdapter

        orderButton.setOnClickListener {
            //TODO 주문 로직 작성 결제하기 기능 구현 해야하는데 서버에서 처리해주어야함
            val payService = PayService()
            val totalAmount = cartItems.sumOf { it.price * it.quantity }

            if (payService.executePayment(totalAmount)){
                val intent = Intent(this@CartActivity, OrderHistoryActivity::class.java)
                Toast.makeText(this, "주문이 접수 되었습니다.", Toast.LENGTH_SHORT).show()

                CartManager.clearCart() //장바구니 내역을 지우고

                startActivity(intent) //주문 내역 으로 이동
            }else{
                Toast.makeText(this, "결제에 실패 하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        updateTotalAmount()
    }

    private fun updateTotalAmount() {
        val totalAmount = cartItems.sumOf { it.price * it.quantity }
        totalAmountTextView.text = "결제금액: ${totalAmount}원"
    }
}