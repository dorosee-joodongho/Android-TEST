package com.example.myapplication.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.MenuDetail

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
            .load(menu.menuImg) // menuImg는 URL 또는 파일 URI
            .placeholder(R.drawable.placeholder_image) // 로딩 중 표시할 이미지
            .error(R.drawable.error_image) // 로드 실패 시 표시할 이미지
            .into(ivMenuImage)

        // 버튼 초기화
        btnRegisterMenu.visibility = View.GONE

        // 메뉴 수정 및 삭제, 품절 처리 버튼은 보이게 설정
        btnDelete.visibility = View.VISIBLE
        btnUpdate.visibility = View.VISIBLE
        btnMarkSoldOut.visibility = View.VISIBLE

        // 품절 처리 버튼 텍스트 변경
        updateSoldOutButton()

        // 메뉴 삭제 버튼 클릭 리스너
        btnDelete.setOnClickListener {
            // 메뉴 삭제 로직 (예시로 Toast 사용)
            Toast.makeText(this, "메뉴가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            finish() // 삭제 후 종료
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

            // 필드들이 모두 입력되었는지 확인
            if (updatedName.isNotEmpty() && updatedPrice != null &&
                updatedDescription.isNotEmpty() && updatedCalorie != null &&
                updatedCarbs != null && updatedProtein != null && updatedFat != null) {

                // 값 업데이트
                menu.name = updatedName
                menu.price = updatedPrice
                menu.description = updatedDescription
                menu.calorie = updatedCalorie
                menu.carbs = updatedCarbs
                menu.protein = updatedProtein
                menu.fat = updatedFat

                // 메뉴 수정 후 성공 메시지
                Toast.makeText(this, "메뉴가 수정되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 품절 처리 버튼 클릭 리스너
        btnMarkSoldOut.setOnClickListener {
            if (menu.isSoldOut == 1) {
                // 품절 해제
                menu.isSoldOut = 0
                Toast.makeText(this, "품절이 해제되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                // 품절 등록
                menu.isSoldOut = 1
                Toast.makeText(this, "품절 처리되었습니다.", Toast.LENGTH_SHORT).show()
            }
            updateSoldOutButton()  // 버튼 텍스트 갱신
        }
    }

    // 품절 처리 버튼 텍스트 변경 함수
    private fun updateSoldOutButton() {
        if (menu.isSoldOut == 1) {
            btnMarkSoldOut.text = "품절 해제"
        } else {
            btnMarkSoldOut.text = "품절 등록"
        }
    }
}

