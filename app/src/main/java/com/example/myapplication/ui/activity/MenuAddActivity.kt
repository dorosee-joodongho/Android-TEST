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
import com.example.myapplication.data.MenuDetailRequest
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.service.MenuDetailService
import com.example.myapplication.utils.ImageUtils
import com.example.myapplication.utils.ToastUtils
import kotlinx.coroutines.launch
import java.io.File

class MenuAddActivity : AppCompatActivity() {
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
    private var selectedImageFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_register)

        val backButton = findViewById<TextView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
        backButton.text = "←  메뉴 등록"

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
            ImageUtils.selectImageFromGallery(this, IMAGE_PICK_REQUEST_CODE)
        }

        // 메뉴 등록 화면이므로 삭제, 수정, 품절 버튼 숨김
        btnDelete.visibility = View.GONE
        btnUpdate.visibility = View.GONE
        btnMarkSoldOut.visibility = View.GONE

        // 메뉴 등록 버튼 클릭 리스너
// 메뉴 등록 버튼 클릭 리스너
        btnRegisterMenu.setOnClickListener {
            val name = menuName.text.toString()
            val price = menuPrice.text.toString().toIntOrNull()
            val description = description.text.toString()
            val calorie = calorie.text.toString().toIntOrNull() ?: 0
            val carbs = carbs.text.toString().toIntOrNull() ?: 0
            val protein = protein.text.toString().toIntOrNull() ?: 0
            val fat = fat.text.toString().toIntOrNull() ?: 0

            // 필드별 유효성 검사
            if (name.isEmpty() || name.length !in 1..20) {
                Toast.makeText(this, "메뉴 이름은 1자 이상 20자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (price == null || price <= 0) {
                Toast.makeText(this, "올바른 메뉴 가격을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (description.isEmpty() || description.length !in 1..200) {
                Toast.makeText(this, "설명은 1자 이상 200자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedImageFile == null) {
                Toast.makeText(this, "이미지를 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (calorie < 0 || carbs < 0 || protein < 0 || fat < 0) {
                Toast.makeText(this, "칼로리, 탄수화물, 단백질, 지방 값은 0 이상의 숫자로 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newMenu = MenuDetailRequest(
                menuId = 1,
                name = name,
                description = description,
                price = price,
                menuImg = selectedImageFile,
                calorie = calorie,
                carbs = carbs,
                protein = protein,
                fat = fat,
                isSoldOut = 0
            )

            // 서버로 데이터 전송 로직
            saveMenuDetail(newMenu)
        }
    }

    private fun saveMenuDetail(newMenu: MenuDetailRequest) {
        lifecycleScope.launch {
            val memberService = MenuDetailService(RetrofitClient.instance)

            try {
                val saveResult = memberService.saveMenuDetail(newMenu)

                if (saveResult) {
                    ToastUtils.showToast(this@MenuAddActivity, "메뉴 저장 성공")
                    startActivity(Intent(this@MenuAddActivity, MenuListActivity::class.java))
                    finish()
                }
            } catch (e: Exception) {
                val errorMessage = e.message ?: "메뉴 저장 중 오류가 발생했습니다. 다시 시도해주세요."
                ToastUtils.showToast(this@MenuAddActivity, errorMessage)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == RESULT_OK) {
            selectedImageUri = data?.data
            ivMenuImage.setImageURI(selectedImageUri)

            // Uri를 File로 변환
            selectedImageFile = ImageUtils.uriToFile(selectedImageUri, this)
        }
    }

    companion object {
        private const val IMAGE_PICK_REQUEST_CODE = 1000
    }
}
