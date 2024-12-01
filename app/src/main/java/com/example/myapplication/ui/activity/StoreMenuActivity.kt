package com.example.myapplication.ui.activity

import StoreMenuAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.CartItem
import com.example.myapplication.data.menu.Menu
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.service.StoreService
import com.example.myapplication.ui.fragment.MenuActionDialogFragment
import com.example.myapplication.ui.manager.CartManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StoreMenuActivity : AppCompatActivity() {

    // StoreService를 ViewModel에서 가져올 수도 있지만, 여기서는 액티비티에서 직접 사용합니다.
    private val storeService = StoreService(RetrofitClient.instance)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_menu)

        val storeId = intent.getLongExtra("storeId", -1L)  // -1L은 default 값, 전달되지 않으면 -1L이 반환됨

        // Store ID가 전달되지 않은 경우 처리
        if (storeId == -1L){
            Toast.makeText(this, "Store ID가 전달되지 않았습니다.", Toast.LENGTH_SHORT).show()
            finish()  // Activity 종료 처리
            return
        }

        // 메뉴 데이터를 비동기적으로 가져오기
        lifecycleScope.launch {
            val menuList = getMenuList(storeId)  // 메뉴 리스트 비동기로 가져오기

            // RecyclerView 설정
            val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this@StoreMenuActivity)
            recyclerView.adapter = StoreMenuAdapter(this@StoreMenuActivity, menuList) { menu ->

                // 메뉴 아이템 클릭 시, 다이얼로그 띄우기
                val dialogFragment = MenuActionDialogFragment(menu) { action ->
                    when (action) {
                        MenuActionDialogFragment.Action.ADD_TO_CART -> {
                            // 장바구니에 추가하는 로직
                            Toast.makeText(this@StoreMenuActivity, "${menu.name}이 장바구니에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                            val quantity = CartManager.getItemQuantity(menu.name, storeId)
                            CartManager.addItem(
                                CartItem(storeId, menu.name, menu.price, quantity)
                            )
                        }
                        MenuActionDialogFragment.Action.ORDER_NOW -> {
                            // 즉시 주문하는 로직
                            Toast.makeText(this@StoreMenuActivity, "${menu.name}이 주문되었습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                dialogFragment.show(supportFragmentManager, "MenuActionDialog")
            }
        }

        val cartButton = findViewById<Button>(R.id.cartButton)

        // 버튼 클릭 이벤트 설정
        cartButton.setOnClickListener {
            // 장바구니 화면으로 이동
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    // 메뉴 리스트를 비동기로 가져오는 함수
    private suspend fun getMenuList(storeId : Long): List<Menu> {
        return withContext(Dispatchers.IO) {
            storeService.getStoreMenuList(storeId)  // 네트워크 요청
        }
    }
}
