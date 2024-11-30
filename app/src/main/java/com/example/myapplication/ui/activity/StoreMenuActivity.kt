package com.example.myapplication.ui.activity

import StoreMenuAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.CartItem
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.service.StoreService
import com.example.myapplication.ui.fragment.MenuActionDialogFragment
import com.example.myapplication.ui.manager.CartManager

class StoreMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_menu)

        val storeId = intent.getLongExtra("storeId", -1L)  // -1L은 default 값, 전달되지 않으면 -1L이 반환됨
        val storeService = StoreService(RetrofitClient.instance)
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
                        val quantity = CartManager.getItemQuantity(menu.name , storeId)
                        CartManager.addItem(
                            CartItem(storeId , menu.name , menu.price , quantity)
                        )
                    }
                    MenuActionDialogFragment.Action.ORDER_NOW -> {
                        // 즉시 주문하는 로직
                        Toast.makeText(this, "${menu.name}이 주문되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            dialogFragment.show(supportFragmentManager, "MenuActionDialog")
        }

        val cartButton = findViewById<Button>(R.id.cartButton)

        // 버튼 클릭 이벤트 설정
        cartButton.setOnClickListener {
            // 장바구니 화면으로 이동
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

}
