package com.example.myapplication.ui.activity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.data.Diet
import com.example.myapplication.data.MenuItem
import com.example.myapplication.network.RetrofitApi
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.service.DietService
import java.time.LocalDate

class DietAddActivity : AppCompatActivity() {
    private val dietService = DietService(RetrofitClient.instance)
    private lateinit var menuListContainer: LinearLayout
    private val menuList = mutableListOf<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_add)

        // 공통 헤더 설정
        val backButton = findViewById<TextView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed() // 뒤로 가기 동작
        }
        backButton.text = "←  식단 기록" // 헤더 제목 변경

        val addButton: Button = findViewById(R.id.add_menu_button)
        val removeButton: Button = findViewById(R.id.remove_menu_button)
        val saveButton: Button = findViewById(R.id.save_button)
        val dateSpinner: Spinner = findViewById(R.id.date_spinner)
        val dietNameInput: EditText = findViewById(R.id.diet_name_input)
        menuListContainer = findViewById(R.id.menu_list_container)

        // 날짜 스피너 설정
        val dates = listOf("2024-11-19", "2024-11-20", "2024-11-21") // 임시 데이터
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dates)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dateSpinner.adapter = adapter

        // + 버튼 클릭
        addButton.setOnClickListener { addMenuItem() }

        // - 버튼 클릭
        removeButton.setOnClickListener { removeMenuItem() }

        // 저장 버튼 클릭
        saveButton.setOnClickListener {
            val selectedDate = LocalDate.parse(dateSpinner.selectedItem.toString()) // 선택된 날짜를 LocalDate로 변환
            val dietName = dietNameInput.text.toString() // 입력된 식단 이름 가져오기

            // 메뉴 데이터를 MenuItem 리스트로 변환
            val menuItems = menuList.map { menu ->
                val menuName = menu.findViewById<EditText>(R.id.menu_name_input).text.toString()
                val calories = menu.findViewById<EditText>(R.id.calories_input).text.toString().toIntOrNull() ?: 0
                val carbs = menu.findViewById<EditText>(R.id.carbs_input).text.toString().toIntOrNull() ?: 0
                val protein = menu.findViewById<EditText>(R.id.protein_input).text.toString().toIntOrNull() ?: 0
                val fat = menu.findViewById<EditText>(R.id.fat_input).text.toString().toIntOrNull() ?: 0

                // MenuItem 객체 생성
                MenuItem(
                    menuName = menuName,
                    calorie = calories,
                    carbs = carbs,
                    protein = protein,
                    fat = fat
                )
            }

            val diet = Diet(
                id = null,
                date = selectedDate,
                dietName = dietName,
                menuItems = menuItems
            )
//            addDiet(diet) // 생성된 Diet 객체 저장 함수 호출
        }

    }

    private fun addMenuItem() {
        val menuItem = layoutInflater.inflate(R.layout.menu_item_layout, menuListContainer, false)
        menuListContainer.addView(menuItem)
        menuList.add(menuItem)
    }

    private fun removeMenuItem() {
        if (menuList.isNotEmpty()) {
            val lastMenu = menuList.removeAt(menuList.size - 1)
            menuListContainer.removeView(lastMenu)
        }
    }

//    식단 정보 추가
//    private fun addDiet(diet: Diet) {
//        dietService.saveDiet(diet) { success -> // 올바른 메서드 이름 사용
//            if (success !== null) {
//                Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "저장 실패, 다시 시도 해주세요.", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
}
