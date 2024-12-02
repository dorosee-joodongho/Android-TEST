package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.storeOrder.StoreMenuItem
import com.example.myapplication.data.storeOrder.StoreOrderDetail
import com.example.myapplication.data.storeOrder.StoreOrderItem
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.service.MemberService
import com.example.myapplication.ui.adapter.StoreOrderAdapter
import com.example.myapplication.ui.fragment.StoreOrderDetailDialogFragment

class StoreOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_order)

        val userService = MemberService(this@StoreOrderActivity, RetrofitClient.instance)
        // 유저 정보 가져오기
        val getCurrentUser = userService.getCurrentUser() // 현재 접속 중인 유저

        // Glide로 유저 이미지 표시
        Glide.with(this)
            .load(getCurrentUser.userImage)  // 로드할 이미지 URL
            .placeholder(R.drawable.ic_launcher_background)  // 로딩 중 이미지
            .error(R.drawable.error_image)  // 이미지 로딩 실패 시 표시할 이미지
            .into(findViewById(R.id.userImage))  // ImageView에 이미지를 로드

        // 유저 이미지 또는 이름 클릭 시 StoreUseMenuActivity 이동
        findViewById<View>(R.id.userImage).setOnClickListener {
            val intent = Intent(this, StoreUseMenuActivity::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.userName).setOnClickListener {
            val intent = Intent(this, StoreUseMenuActivity::class.java)
            startActivity(intent)
        }

        val storeOrderRecyclerView: RecyclerView = findViewById(R.id.storeOrderRecyclerView)

        val dummyOrders = listOf(
            StoreOrderItem(1, "2024-12-01", 15000, "접수 완료"),
            StoreOrderItem(2, "2024-12-01", 20000, "취소"),
            StoreOrderItem(3, "2024-12-02", 12000, "접수 완료")
        )

        val adapter = StoreOrderAdapter(dummyOrders) { storeOrderItem ->
            showStoreOrderDetailModal(storeOrderItem)
        }

        storeOrderRecyclerView.layoutManager = LinearLayoutManager(this)
        storeOrderRecyclerView.adapter = adapter
    }

    private fun showStoreOrderDetailModal(storeOrderItem: StoreOrderItem) {
        val dummyOrderDetail = StoreOrderDetail(
            "접수 완료",
            listOf(
                StoreMenuItem("햄버거", 5000, 2, 10000),
                StoreMenuItem("감자튀김", 3000, 1, 3000)
            ),
            13000,
            "2024-12-01 12:34",
            "김XX",
            "010-1234-5678"
        )

        val dialog = StoreOrderDetailDialogFragment.newInstance(dummyOrderDetail)
        dialog.show(supportFragmentManager, "StoreOrderDetailDialog")
    }
}
