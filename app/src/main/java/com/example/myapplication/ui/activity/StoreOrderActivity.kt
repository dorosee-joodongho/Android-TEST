package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
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

        val userName: TextView = findViewById(R.id.userName)

        val userService = MemberService(this@StoreOrderActivity, RetrofitClient.instance)

        lifecycleScope.launch {
            // 유저 정보 가져오기
            val getCurrentUser = userService.getMember()

            if (getCurrentUser != null) {
                userName.text = getCurrentUser.memberName

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
            response?.sortedByDescending { it.orderDate }
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
            // 기존 아이템 업데이트
            currentOrders[existingIndex] = updatedOrder
            storeOrderAdapter.notifyItemChanged(existingIndex)
        } else {
            // 새로운 아이템을 리스트의 맨 앞에 추가
            currentOrders.add(0, updatedOrder)
            storeOrderAdapter.notifyItemInserted(0)
        }

        // 업데이트된 데이터로 어댑터 갱신
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


