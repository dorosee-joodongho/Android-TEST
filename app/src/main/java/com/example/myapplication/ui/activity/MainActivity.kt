package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.network.StompManager
import com.example.myapplication.service.MemberService
import com.example.myapplication.service.MenuService
import com.example.myapplication.service.StoreService
import com.example.myapplication.ui.adapter.ImageAdapter
import com.example.myapplication.ui.adapter.StoreAdapter
import com.example.myapplication.ui.listener.OnItemClickListener
import com.example.myapplication.utils.ToastUtils
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val menuRecyclerView: RecyclerView = findViewById(R.id.todaySpecialMenuListRecyclerView)
        val storeRecyclerView: RecyclerView = findViewById(R.id.storeListRecyclerView)
        val userName: TextView = findViewById(R.id.userName)

        // 서비스 인스턴스 생성
        val menuService = MenuService(RetrofitClient.instance)
        val storeService = StoreService(RetrofitClient.instance)
        val userService = MemberService(this@MainActivity, RetrofitClient.instance)

        lifecycleScope.launch {
            // 유저 정보 가져오기
            val getCurrentUser = userService.getMember()

            if (getCurrentUser != null) {
                userName.text = getCurrentUser.memberName

                Glide.with(this@MainActivity)
                    .load(getCurrentUser.userImage)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.error_image)
                    .into(findViewById(R.id.userImage))

                findViewById<View>(R.id.userImage).setOnClickListener {
                    startActivity(Intent(this@MainActivity, UserUseMenuActivity::class.java))
                }
                findViewById<View>(R.id.userName).setOnClickListener {
                    startActivity(Intent(this@MainActivity, UserUseMenuActivity::class.java))
                }
            } else {
                ToastUtils.showToast(this@MainActivity, "유저 정보를 가져오는 데 실패했습니다.")
            }
        }

        // RecyclerView 어댑터 설정
        menuRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        storeRecyclerView.layoutManager = GridLayoutManager(this, 2)

        lifecycleScope.launch {
            // 비동기적으로 오늘 추천 메뉴 데이터 가져오기
            val todaySpecialInfoList = menuService.getTodayBestMenu() // 오늘 추천 메뉴 리스트
            if (todaySpecialInfoList != null) {
                menuRecyclerView.adapter = ImageAdapter(todaySpecialInfoList, object : OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        val todaySpecialMenu = todaySpecialInfoList[position]
                        val intent = Intent(this@MainActivity, StoreMenuActivity::class.java)
                        println(todaySpecialMenu.storeId)
                        intent.putExtra("storeId", todaySpecialMenu.storeId)
                        startActivity(intent)
                    }
                })
            }

            // 비동기적으로 근처 가게 목록 데이터 가져오기
            val nearbyStores = storeService.getNearbyStores() // 근처 가게 목록
            storeRecyclerView.adapter = StoreAdapter(nearbyStores, object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val store = nearbyStores[position]
                    val intent = Intent(this@MainActivity, StoreMenuActivity::class.java)
                    println(store.storeId)
                    intent.putExtra("storeId", store.storeId.toLong())
                    startActivity(intent)
                }
            })
        }
    }
}
