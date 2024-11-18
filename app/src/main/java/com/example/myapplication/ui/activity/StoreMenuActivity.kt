package com.example.myapplication.ui.activity

import StoreMenuAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.service.StoreService
import com.example.myapplication.ui.fragment.MenuActionDialogFragment

class StoreMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_menu)

        val storeId = intent.getLongExtra("storeId", -1L)  // -1L은 default 값, 전달되지 않으면 -1L이 반환됨
        val storeService = StoreService()
        //전송 x 방어 처리
        if (storeId == -1L){
            Toast.makeText(this, "Store ID가 전달되지 않았습니다.", Toast.LENGTH_SHORT).show()
        }

        val menuList = storeService.getStoreMenuList(storeId) // 메뉴 리스트 가져오기

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = StoreMenuAdapter(this, menuList) { menu ->

            // 메뉴 아이템 클릭 시, 다이얼로그 띄우기
            val dialogFragment = MenuActionDialogFragment(menu) { action ->
                when (action) {
                    MenuActionDialogFragment.Action.ADD_TO_CART -> {
                        // 장바구니에 추가하는 로직
                        Toast.makeText(this, "${menu.name}이 장바구니에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    MenuActionDialogFragment.Action.ORDER_NOW -> {
                        // 즉시 주문하는 로직
                        Toast.makeText(this, "${menu.name}이 주문되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            dialogFragment.show(supportFragmentManager, "MenuActionDialog")
        }
    }

}
