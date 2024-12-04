package com.example.myapplication.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.storeOrder.StoreOrderItem
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.network.StompManager
import com.example.myapplication.service.MemberService
import com.example.myapplication.service.StoreOrderService
import com.example.myapplication.ui.adapter.StoreOrderAdapter
import com.example.myapplication.ui.fragment.StoreOrderDetailDialogFragment
import com.example.myapplication.utils.ToastUtils
import kotlinx.coroutines.launch

class StoreOrderActivity : AppCompatActivity(), StoreOrderDetailDialogFragment.OnOrderCancelListener {
    private lateinit var stompManager: StompManager // 소켓
    private val storeOrderService = StoreOrderService(RetrofitClient.instance)
    private lateinit var storeOrderAdapter: StoreOrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_order)

        val userService = MemberService(this@StoreOrderActivity, RetrofitClient.instance)

        lifecycleScope.launch {
            // 유저 정보 가져오기
            val getCurrentUser = userService.getMember()

            if (getCurrentUser != null) {
                Glide.with(this@StoreOrderActivity)
                    .load(getCurrentUser.userImage)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.error_image)
                    .into(findViewById(R.id.userImage))

                findViewById<View>(R.id.userImage).setOnClickListener {
                    startActivity(Intent(this@StoreOrderActivity, StoreUseMenuActivity::class.java))
                }
                findViewById<View>(R.id.userName).setOnClickListener {
                    startActivity(Intent(this@StoreOrderActivity, StoreUseMenuActivity::class.java))
                }

                stompManager = StompManager()
                stompManager.connect(getCurrentUser.memberID.toString()) { message ->
                    runOnUiThread {
                        try {
                            // 메시지 JSON 파싱
                            val updatedOrder = parseOrderMessage(message)
                            println("주문 알람 : ${updatedOrder}")
                            if (updatedOrder != null) {
                                ToastUtils.showToast(this@StoreOrderActivity, "새로운 주문이 생성되었습니다.")
                                updateOrderInAdapter(updatedOrder)
                            }
                        } catch (e: Exception) {
                            ToastUtils.showToast(this@StoreOrderActivity, "메시지 처리 오류: ${e.message}")
                        }
                    }
                }

            } else {
                ToastUtils.showToast(this@StoreOrderActivity, "유저 정보를 가져오는 데 실패했습니다.")
            }
        }

        val storeOrderRecyclerView: RecyclerView = findViewById(R.id.storeOrderRecyclerView)

        lifecycleScope.launch {
            val orders = getOrderList()

            storeOrderAdapter = StoreOrderAdapter(orders ?: emptyList()) { storeOrderItem ->
                showStoreOrderDetailModal(storeOrderItem)
            }

            storeOrderRecyclerView.layoutManager = LinearLayoutManager(this@StoreOrderActivity)
            storeOrderRecyclerView.adapter = storeOrderAdapter
        }
    }

    private fun showStoreOrderDetailModal(storeOrderItem: StoreOrderItem) {
        lifecycleScope.launch {
            val orderDetail = storeOrderService.getOrderDetail(storeOrderItem.orderId)

            if (orderDetail != null) {
                val dialog = StoreOrderDetailDialogFragment.newInstance(
                    orderDetail,
                    storeOrderItem.status,
                    storeOrderItem.orderId
                )
                dialog.show(supportFragmentManager, "StoreOrderDetailDialog")
                dialog.setTargetFragment(null, 0) // 추가된 Listener 설정
            }
        }
    }

    private suspend fun getOrderList(): List<StoreOrderItem>? {
        return try {
            val response = storeOrderService.getStoreOrderList()
            response
        } catch (e: Exception) {
            ToastUtils.showToast(this@StoreOrderActivity, "${e.message}")
            null
        }
    }

    private fun parseOrderMessage(message: String): StoreOrderItem? {
        return try {
            val gson = com.google.gson.Gson()
            gson.fromJson(message, StoreOrderItem::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun updateOrderInAdapter(updatedOrder: StoreOrderItem) {
        val currentOrders = storeOrderAdapter.orders.toMutableList()
        val existingIndex = currentOrders.indexOfFirst { it.orderId == updatedOrder.orderId }

        if (existingIndex != -1) {
            currentOrders[existingIndex] = updatedOrder
            storeOrderAdapter.notifyItemChanged(existingIndex)
        } else {
            currentOrders.add(updatedOrder)
            storeOrderAdapter.notifyItemInserted(currentOrders.size - 1)
        }

        storeOrderAdapter.updateData(currentOrders)
    }


    override fun onOrderCanceled() {
        refreshOrders()
    }

    private fun refreshOrders() {
        lifecycleScope.launch {
            val updatedOrders = getOrderList()
            storeOrderAdapter.updateData(updatedOrders ?: emptyList())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stompManager.disconnect()
    }
}


