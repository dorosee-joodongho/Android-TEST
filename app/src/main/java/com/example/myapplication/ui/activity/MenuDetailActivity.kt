package com.example.myapplication.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.myapplication.R
import com.example.myapplication.data.MenuDetail
import com.example.myapplication.data.MenuDetailRequest
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.service.MenuDetailService
import com.example.myapplication.utils.ImageUtils
import com.example.myapplication.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

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

    private var imageFile: File? = null

    private val menuService = MenuDetailService(RetrofitClient.instance)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_register)

        val backButton = findViewById<TextView>(R.id.backButton)
        backButton.setOnClickListener { onBackPressed() }
        backButton.text = "←  메뉴 조회"

        // 데이터 바인딩
        initViews()

        // MenuDetail 객체 받기
        menu = intent.getSerializableExtra("menu") as MenuDetail

        // 기존 값 세팅
        menuName.setText(menu.menuName)
        menuPrice.setText(menu.menuPrice.toString())
        description.setText(menu.menuDescription)
        calorie.setText(menu.calorie.toString())
        carbs.setText(menu.carbs.toString())
        protein.setText(menu.protein.toString())
        fat.setText(menu.fat.toString())


        // 이미지 로드 및 File 변환
        loadMenuImage(menu.menuImage)

        // 버튼 초기화
        btnRegisterMenu.visibility = View.GONE
        btnDelete.visibility = View.VISIBLE
        btnUpdate.visibility = View.VISIBLE
        btnMarkSoldOut.visibility = View.VISIBLE
        updateSoldOutButton()

        // 버튼
        btnDelete.setOnClickListener { deleteMenu(menu.menuId!!) }
        btnUpdate.setOnClickListener { updateMenuData() }
        btnMarkSoldOut.setOnClickListener {
            menu.isSoldOut = if (menu.isSoldOut == 1) 0 else 1
            updateSoldOutButton()
            updateMenuData(true)
        }
        btnSelectImage.setOnClickListener { ImageUtils.selectImageFromGallery(this, IMAGE_PICK_REQUEST_CODE) }
    }

    private fun loadMenuImage(url: String?) {
        lifecycleScope.launch {
            try {
                val file = withContext(Dispatchers.IO) {
                    Glide.with(this@MenuDetailActivity)
                        .downloadOnly()
                        .load(url)
                        .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get()
                }
                imageFile = file
                Glide.with(this@MenuDetailActivity)
                    .load(file)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(ivMenuImage)
            } catch (e: Exception) {
                ToastUtils.showToast(this@MenuDetailActivity, "이미지 로드 실패: ${e.message}")
            }
        }
    }

    private fun updateSoldOutButton() {
        btnMarkSoldOut.text = if (menu.isSoldOut == 1) "품절 해제" else "품절 등록"
    }

    private fun updateMenuData(stayOnScreen: Boolean = false) {
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
            val updateMenu = MenuDetailRequest(
                menuId = menu.menuId,
                menuImg = imageFile, // 기존 이미지 유지
                name = updatedName,
                isSoldOut = menu.isSoldOut, // 변경된 품절 상태 사용
                fat = updatedFat,
                description = updatedDescription,
                carbs = updatedCarbs,
                price = updatedPrice,
                calorie = updatedCalorie,
                protein = updatedProtein,
            )

            lifecycleScope.launch {
                try {
                    val isUpdated = menuService.updateMenuDetail(updateMenu)
                    if (isUpdated == true) {
                        ToastUtils.showToast(this@MenuDetailActivity, "메뉴가 성공적으로 수정되었습니다.")
                        if (!stayOnScreen) finish()
                    } else {
                        ToastUtils.showToast(this@MenuDetailActivity, "메뉴 수정에 실패했습니다.")
                    }
                } catch (e: Exception) {
                    ToastUtils.showToast(this@MenuDetailActivity, "수정 중 오류 발생: ${e.message}")
                }
            }
        } else {
            ToastUtils.showToast(this, "모든 필드를 입력해주세요.")
        }
    }


    private fun deleteMenu(menuId: Long) {
        lifecycleScope.launch {
            try {
                val isDeleted = menuService.deleteMenuDetail(menuId)
                if (isDeleted == true) {
                    ToastUtils.showToast(this@MenuDetailActivity, "메뉴가 성공적으로 삭제되었습니다.")
                    val resultIntent = Intent().apply {
                        putExtra("deletedMenuId", menuId)
                    }
                    setResult(RESULT_OK, resultIntent)
                    finish()
                } else {
                    ToastUtils.showToast(this@MenuDetailActivity, "메뉴 삭제에 실패했습니다.")
                }
            } catch (e: Exception) {
                ToastUtils.showToast(this@MenuDetailActivity, "삭제 중 오류 발생: ${e.message}")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == RESULT_OK) {
            val selectedUri = data?.data
            selectedUri?.let {
                ivMenuImage.setImageURI(it)
                imageFile = ImageUtils.uriToFile(it, this) // 새 이미지 파일로 교체
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

    companion object {
        private const val IMAGE_PICK_REQUEST_CODE = 1000
    }
}
