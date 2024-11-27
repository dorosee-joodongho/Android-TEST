package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.MenuDetail
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.service.MenuDetailService
import com.example.myapplication.ui.adapter.MenuAdapter
import kotlinx.coroutines.launch

class MenuListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAddMenu: Button
    private val menuService = MenuDetailService(RetrofitClient.instance)
    private lateinit var menuList: List<MenuDetail> // 메뉴 데이터 리스트

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_list)

        val backButton = findViewById<TextView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
        backButton.text = "←  메뉴 목록"

        recyclerView = findViewById(R.id.recyclerViewMenu)
        btnAddMenu = findViewById(R.id.btnAddMenu)

        // RecyclerView 초기화
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchMenuData()

        btnAddMenu.setOnClickListener {
            // menuList가 초기화되고 비어있지 않은지 확인
            if (::menuList.isInitialized && menuList.isNotEmpty()) {
                val firstStoreId = menuList.first().storeId
                val intent = Intent(this, MenuAddActivity::class.java).apply {
                    putExtra("storeId", firstStoreId)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "메뉴 데이터를 로드할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchMenuData() {
        lifecycleScope.launch {
            try {
                // 메뉴 데이터를 비동기적으로 가져옴
                val data = menuService.getMenuDetailList()

                if (data != null && data.isNotEmpty()) {
                    menuList = data.toMutableList()
                    setupRecyclerView(menuList)  // 리사이클러뷰 업데이트
                } else {
                    Toast.makeText(this@MenuListActivity, "메뉴 데이터가 비어 있습니다.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // 오류 처리
                Toast.makeText(this@MenuListActivity, "메뉴 데이터를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView(menuList: List<MenuDetail>) {
        val adapter = MenuAdapter(menuList) { selectedMenu ->
            val intent = Intent(this, MenuDetailActivity::class.java)
            intent.putExtra("menu", selectedMenu)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }
}
