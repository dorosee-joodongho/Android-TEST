package com.example.myapplication.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.data.MenuDetail
import com.example.myapplication.service.MenuDetailService
import com.example.myapplication.utils.ToastUtils
import kotlinx.coroutines.launch

class MenuAddActivity : AppCompatActivity() {
    private var storeId: Long = -1L // 기본값

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

    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_register)

        val backButton = findViewById<TextView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
        backButton.text = "←  메뉴 등록"

        // Intent로 전달된 storeId 값 가져오기
        storeId = intent.getLongExtra("storeId", -1L)

        if (storeId != -1L) {
            Toast.makeText(this, "Store ID: $storeId", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Store ID를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
        }

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


        btnSelectImage.setOnClickListener {
            selectImageFromGallery()
        }

        // 메뉴 등록 화면이므로 삭제, 수정, 품절 버튼 숨김
        btnDelete.visibility = View.GONE
        btnUpdate.visibility = View.GONE
        btnMarkSoldOut.visibility = View.GONE

        // 메뉴 등록 버튼 클릭 리스너
        btnRegisterMenu.setOnClickListener {
            val name = menuName.text.toString()
            val price = menuPrice.text.toString().toIntOrNull()
            val description = description.text.toString()
            val calorie = calorie.text.toString().toIntOrNull() ?: 0
            val carbs = carbs.text.toString().toIntOrNull() ?: 0
            val protein = protein.text.toString().toIntOrNull() ?: 0
            val fat = fat.text.toString().toIntOrNull() ?: 0

            if (name.isNotEmpty() && price != null && selectedImageUri != null) {
                val newMenu = MenuDetail(
                    menuId = null,
                    storeId = 1, // 임시
                    name = name,
                    description = description,
                    price = price,
                    menuImg = selectedImageUri,
                    calorie = calorie,
                    carbs = carbs,
                    protein = protein,
                    fat = fat,
                    isSoldOut = 0
                )

                // 서버로 데이터 전송 로직
//                saveMenuDetail(newMenu)
            } else {
                Toast.makeText(this, "모든 필드를 입력하고 이미지를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun saveMenuDetail(newMenu: MenuDetail) {
        lifecycleScope.launch {
            val memberService = MenuDetailService()

            try {
                val loginResult = memberService.saveMenuDetail(newMenu)

                if (loginResult != null) {
                    ToastUtils.showToast(this@MenuAddActivity,"메뉴 저장 성공")
                    startActivity(Intent(this@MenuAddActivity, MenuListActivity::class.java))
                    finish()
                } else {
                    ToastUtils.showToast(this@MenuAddActivity,"메뉴 저장 실패")
                }
            } catch (e: Exception) {
                val errorMessage = e.message ?: "메뉴 저장 중 오류가 발생했습니다.. 다시 시도해주세요."
                ToastUtils.showToast(this@MenuAddActivity, errorMessage)
            }
        }
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == RESULT_OK) {
            selectedImageUri = data?.data
            ivMenuImage.setImageURI(selectedImageUri)
        }
    }

    companion object {
        private const val IMAGE_PICK_REQUEST_CODE = 1000
    }

}
