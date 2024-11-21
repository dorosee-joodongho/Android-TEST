package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.service.MemberService
import com.example.myapplication.service.MenuService
import com.example.myapplication.service.StoreService
import com.example.myapplication.ui.adapter.ImageAdapter
import com.example.myapplication.ui.adapter.StoreAdapter
import com.example.myapplication.ui.listener.OnItemClickListener

class MainActivity : ComponentActivity(
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val menuRecyclerView: RecyclerView = findViewById(R.id.todaySpecialMenuListRecyclerView)
        val storeRecyclerView: RecyclerView = findViewById(R.id.storeListRecyclerView)

        val menuService = MenuService()
        val userService = MemberService()
        val storeService = StoreService()

        val getCurrentUser = userService.getCurrentUser() //현재 접속 중인 유저
        val todaySpecialInfoList = menuService.getTodayBestMenu() //오늘 추천 메뉴 리스트
        val nearbyStores = storeService.getNearbyStores() //근처 가게 목록들

        //유저 이미지 표시
        Glide.with(this)
            .load(getCurrentUser.userImage)  // 로드할 이미지 URL
            .placeholder(R.drawable.ic_launcher_background)  // 로딩 중에 표시할 이미지
            .error(R.drawable.error_image)  // 이미지 로딩 실패 시 표시할 이미지
            .into(findViewById(R.id.userImage))  // ImageView에 이미지를 로드

        // 이미지 추천 메뉴 그리는 부분
        menuRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        menuRecyclerView.adapter = ImageAdapter(todaySpecialInfoList, object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val todaySpecialMenu = todaySpecialInfoList[position]
                // 주문 페이지로 이동, 메뉴 ID를 전달
                val intent = Intent(this@MainActivity, StoreMenuActivity::class.java)  // 'this@YourActivity'로 Context 명시
                intent.putExtra("storeId", todaySpecialMenu.storeId)  // 메뉴 ID 전달
                startActivity(intent)
            }
        })

        // GridLayoutManager를 사용하여 2열로 표시
        storeRecyclerView.layoutManager = GridLayoutManager(this, 2)
        storeRecyclerView.adapter = StoreAdapter(nearbyStores, object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                // 클릭된 항목의 store.id 가져오기
                val store = nearbyStores[position]
                val intent = Intent(this@MainActivity, StoreMenuActivity::class.java)  // 'this@YourActivity'로 Context 명시
                intent.putExtra("storeId", store.storeId)  // store ID 전달
                startActivity(intent)
            }
        })


        // 유저 이름 또는 이미지 클릭 이벤트 처리
        findViewById<View>(R.id.userImage).setOnClickListener {
            // UserProfileActivity로 이동
            val intent = Intent(this, UserMenuActivity::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.userName).setOnClickListener {
            // UserProfileActivity로 이동
            val intent = Intent(this, UserMenuActivity::class.java)
            startActivity(intent)
        }


    } // onCreate
}
