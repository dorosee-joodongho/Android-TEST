package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
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

class StoreOrderActivity : AppCompatActivity() {
    private lateinit var stompManager: StompManager // 소켓
    private val storeOrderService = StoreOrderService(RetrofitClient.instance)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_order)

        val userService = MemberService(this@StoreOrderActivity, RetrofitClient.instance)
        lifecycleScope.launch {
            // 유저 정보 가져오기
            val getCurrentUser = userService.getMember()

            if (getCurrentUser != null) {
                // Glide로 유저 이미지 표시
                Glide.with(this@StoreOrderActivity)
                    .load(getCurrentUser.userImage) // 로드할 이미지 URL
                    .placeholder(R.drawable.ic_launcher_background) // 로딩 중 이미지
                    .error(R.drawable.error_image) // 이미지 로딩 실패 시 표시할 이미지
                    .into(findViewById(R.id.userImage)) // ImageView에 이미지를 로드

                // 유저 이미지 또는 이름 클릭 시 StoreUseMenuActivity 이동
                findViewById<View>(R.id.userImage).setOnClickListener {
                    val intent = Intent(this@StoreOrderActivity, StoreUseMenuActivity::class.java)
                    startActivity(intent)
                }
                findViewById<View>(R.id.userName).setOnClickListener {
                    val intent = Intent(this@StoreOrderActivity, StoreUseMenuActivity::class.java)
                    startActivity(intent)
                }

                // STOMP 연결
                stompManager = StompManager()
                stompManager.connect(getCurrentUser.memberID.toString()) { message ->
                    runOnUiThread {
                        println("Received message: $message")
                        // UI 업데이트 하기
                    }
                }
            } else {
                ToastUtils.showToast(this@StoreOrderActivity, "유저 정보를 가져오는 데 실패했습니다.")
            }
        }

        val storeOrderRecyclerView: RecyclerView = findViewById(R.id.storeOrderRecyclerView)

        // getOrderList를 비동기적으로 호출하여 결과를 RecyclerView에 반영
        lifecycleScope.launch {
            val orders = getOrderList()

            // 어댑터에 데이터를 설정
            val adapter = StoreOrderAdapter(orders ?: emptyList()) { storeOrderItem ->
                showStoreOrderDetailModal(storeOrderItem)
            }

            storeOrderRecyclerView.layoutManager = LinearLayoutManager(this@StoreOrderActivity)
            storeOrderRecyclerView.adapter = adapter
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

    override fun onDestroy() {
        super.onDestroy()
        stompManager.disconnect()
    }
}

