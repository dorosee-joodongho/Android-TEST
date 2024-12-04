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
    private lateinit var adapter: MenuAdapter // RecyclerView 어댑터

    override fun onResume() {
        super.onResume()
        fetchMenuData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_list)

        val backButton = findViewById<TextView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
        backButton.text = "←  장바구니"

        recyclerView = findViewById(R.id.recyclerViewMenu)
        btnAddMenu = findViewById(R.id.btnAddMenu)

        // RecyclerView 초기화
        adapter = MenuAdapter(emptyList()) { selectedMenu ->
            val intent = Intent(this, MenuDetailActivity::class.java)
            intent.putExtra("menu", selectedMenu)
            startActivityForResult(intent, MENU_DETAIL_REQUEST_CODE)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fetchMenuData()

        btnAddMenu.setOnClickListener {
            val intent = Intent(this@MenuListActivity, MenuAddActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchMenuData() {
        lifecycleScope.launch {
            try {
                // 메뉴 데이터를 비동기적으로 가져옴
                val data = menuService.getMenuDetailList()

                if (data != null && data.isNotEmpty()) {
                    menuList = data.toMutableList()
                    adapter.updateMenuList(menuList) // 어댑터 데이터 갱신
                } else {
                    Toast.makeText(this@MenuListActivity, "메뉴 데이터가 비어 있습니다.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // 오류 처리
                Toast.makeText(this@MenuListActivity, "메뉴 데이터를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MENU_DETAIL_REQUEST_CODE && resultCode == RESULT_OK) {
            val deletedMenuId = data?.getLongExtra("deletedMenuId", -1)
            if (deletedMenuId != null && deletedMenuId != -1L) {
                // 삭제된 메뉴를 리스트에서 제거
                menuList = menuList.filter { it.menuId != deletedMenuId }
                adapter.updateMenuList(menuList)
            }
        }
    }

    companion object {
        private const val MENU_DETAIL_REQUEST_CODE = 1001
    }

}
