package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.MenuDetail
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.service.MenuDetailService
import com.example.myapplication.utils.ToastUtils
import kotlinx.coroutines.launch

class MenuDetailActivity : AppCompatActivity() {
    private lateinit var menu: MenuDetail
    private lateinit var menuName: EditText
    private lateinit var menuPrice: EditText
    private lateinit var description: EditText
    private lateinit var calorie: EditText
    private lateinit var carbs: EditText
    private lateinit var protein: EditText
    private lateinit var fat: EditText
    private lateinit var btnRegisterMenu: Button
    private lateinit var btnDelete: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnMarkSoldOut: Button
    private lateinit var ivMenuImage: ImageView
    private lateinit var btnSelectImage: Button

    private val menuService = MenuDetailService(RetrofitClient.instance)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_register)

        val backButton = findViewById<TextView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
        backButton.text = "←  메뉴 조회"

        // MenuDetail 객체 받기
        menu = intent.getSerializableExtra("menu") as MenuDetail

        // 데이터 바인딩
        initViews()

        // 기존 값들을 EditText에 세팅
        menuName.setText(menu.name)
        menuPrice.setText(menu.price.toString())
        description.setText(menu.description)
        calorie.setText(menu.calorie.toString())
        carbs.setText(menu.carbs.toString())
        protein.setText(menu.protein.toString())
        fat.setText(menu.fat.toString())

        // 이미지 로드 (Glide 사용)
        Glide.with(this)
            .load(menu.menuImg)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(ivMenuImage)

        // 버튼 초기화
        btnRegisterMenu.visibility = View.GONE
        btnDelete.visibility = View.VISIBLE
        btnUpdate.visibility = View.VISIBLE
        btnMarkSoldOut.visibility = View.VISIBLE
        updateSoldOutButton()

        // 메뉴 삭제 버튼 클릭 리스너
        btnDelete.setOnClickListener {
            deleteMenu(menu.menuId!!)
        }

        // 메뉴 수정 버튼 클릭 리스너
        btnUpdate.setOnClickListener {
            val updatedName = menuName.text.toString()
            val updatedPrice = menuPrice.text.toString().toIntOrNull()
            val updatedDescription = description.text.toString()
            val updatedCalorie = calorie.text.toString().toIntOrNull()
            val updatedCarbs = carbs.text.toString().toIntOrNull()
            val updatedProtein = protein.text.toString().toIntOrNull()
            val updatedFat = fat.text.toString().toIntOrNull()

            if (updatedName.isNotEmpty() && updatedPrice != null &&
                updatedDescription.isNotEmpty() && updatedCalorie != null &&
                updatedCarbs != null && updatedProtein != null && updatedFat != null
            ) {
                menu.name = updatedName
                menu.price = updatedPrice
                menu.description = updatedDescription
                menu.calorie = updatedCalorie
                menu.carbs = updatedCarbs
                menu.protein = updatedProtein
                menu.fat = updatedFat

                updateMenu(menu)
            } else {
                ToastUtils.showToast(this, "모든 필드를 입력해주세요.")
            }
        }

        // 품절 처리 버튼 클릭 리스너
        btnMarkSoldOut.setOnClickListener {
            menu.isSoldOut = if (menu.isSoldOut == 1) 0 else 1
            updateMenu(menu)
            updateSoldOutButton()
        }
    }

    // 품절 처리 버튼 텍스트 변경 함수
    private fun updateSoldOutButton() {
        btnMarkSoldOut.text = if (menu.isSoldOut == 1) "품절 해제" else "품절 등록"
    }

    // 메뉴 수정 로직
    private fun updateMenu(menuDetail: MenuDetail) {
        lifecycleScope.launch {
            try {
                val isUpdated = menuService.updateMenuDetail(menuDetail)
                if (isUpdated == true) {
                    ToastUtils.showToast(this@MenuDetailActivity, "메뉴가 성공적으로 수정되었습니다.")
                    finish()
                } else {
                    ToastUtils.showToast(this@MenuDetailActivity, "메뉴 수정에 실패했습니다.")
                }
            } catch (e: Exception) {
                ToastUtils.showToast(this@MenuDetailActivity, "수정 중 오류 발생: ${e.message}")
            }
        }
    }

    // 메뉴 삭제 로직
    private fun deleteMenu(menuId: Long) {
        lifecycleScope.launch {
            try {
                val isDeleted = menuService.deleteMenuDetail(menuId)
                if (isDeleted == true) {
                    ToastUtils.showToast(this@MenuDetailActivity, "메뉴가 성공적으로 삭제되었습니다.")
                    finish()
                } else {
                    ToastUtils.showToast(this@MenuDetailActivity, "메뉴 삭제에 실패했습니다.")
                }
            } catch (e: Exception) {
                ToastUtils.showToast(this@MenuDetailActivity, "삭제 중 오류 발생: ${e.message}")
            }
        }
    }

    private fun initViews() {
        menuName = findViewById(R.id.menuName)
        menuPrice = findViewById(R.id.price)
        description = findViewById(R.id.description)
        calorie = findViewById(R.id.calorie)
        carbs = findViewById(R.id.carbs)
        protein = findViewById(R.id.protein)
        fat = findViewById(R.id.fat)
        btnRegisterMenu = findViewById(R.id.btnRegisterMenu)
        btnDelete = findViewById(R.id.btnDelete)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnMarkSoldOut = findViewById(R.id.btnMarkSoldOut)
        ivMenuImage = findViewById(R.id.ivMenuImage)
        btnSelectImage = findViewById(R.id.btnSelectImage)
    }
}
