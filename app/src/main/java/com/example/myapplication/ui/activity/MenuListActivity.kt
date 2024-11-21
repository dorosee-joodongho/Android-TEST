package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.MenuDetail
import com.example.myapplication.service.MenuDetailService
import com.example.myapplication.ui.adapter.MenuAdapter

class MenuListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAddMenu: Button
    private val menuService = MenuDetailService() // MenuDetailService 인스턴스 생성
    private lateinit var menuList: MutableList<MenuDetail> // 메뉴 데이터 리스트

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

        // 서비스에서 데이터 가져오기
        menuService.getMenuDetailsList { data ->
            if (data != null) {
                menuList = data.toMutableList()
                setupRecyclerView(menuList)
            } else {
                Toast.makeText(this, "메뉴 데이터를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        // "새로운 메뉴 등록하기" 버튼 클릭 리스너
        btnAddMenu.setOnClickListener {
            val intent = Intent(this, MenuRegisterActivity::class.java)
            startActivity(intent)
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
