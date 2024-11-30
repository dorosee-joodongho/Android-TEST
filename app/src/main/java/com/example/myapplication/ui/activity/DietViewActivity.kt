package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.data.Diet
import com.example.myapplication.data.MenuItem
import com.example.myapplication.ui.viewModel.DietViewModel
import java.time.LocalDate

class DietViewActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var totalMonthlyCaloriesText: TextView
    private lateinit var menuListContainer: LinearLayout
    private lateinit var totalCaloriesRow: TextView
    private lateinit var totalCarbsRow: TextView
    private lateinit var totalProteinRow: TextView
    private lateinit var totalFatRow: TextView
    private lateinit var dietAnalysisButton: Button

    private val viewModel: DietViewModel by viewModels()

    private var dietsForSelectedDate: List<Diet> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_view)

        initViews()
        observeViewModel()
        setupListeners()

        // 기본 날짜를 현재 날짜로 설정
        viewModel.setSelectedDate(LocalDate.now())

        // 뒤로 가는 버튼 설정
        val backButton = findViewById<TextView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
        backButton.text = "←  내 식단"
    }

    private fun initViews() {
        calendarView = findViewById(R.id.calendar_view)
        totalMonthlyCaloriesText = findViewById(R.id.total_calories_text)
        menuListContainer = findViewById(R.id.menu_list_container)
        totalCaloriesRow = findViewById(R.id.total_calories_row)
        totalCarbsRow = findViewById(R.id.total_carbs_row)
        totalProteinRow = findViewById(R.id.total_protein_row)
        totalFatRow = findViewById(R.id.total_fat_row)
        dietAnalysisButton = findViewById(R.id.diet_analysis_button)
    }

    private fun observeViewModel() {
        // 현재 선택된 날짜에 해당하는 식단을 관찰
        viewModel.currentDiet.observe(this, Observer { diets ->
            dietsForSelectedDate = diets ?: emptyList()

            if (dietsForSelectedDate.isEmpty()) {
                showNoDietMessage()
            } else {
                displayCurrentDiet()
            }
        })

        // 한 달 동안의 총 칼로리 수를 관찰
        viewModel.monthlyCalories.observe(this, Observer { totalCalories ->
            totalMonthlyCaloriesText.text = "총 칼로리: $totalCalories kcal"
        })
    }


    private fun setupListeners() {
        // 달력에서 날짜를 선택하면 해당 날짜로 업데이트
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDay = LocalDate.of(year, month + 1, dayOfMonth)
            viewModel.setSelectedDate(selectedDay)
        }

        dietAnalysisButton.setOnClickListener {
            val intent = Intent(this, DietAnalysisActivity::class.java)
            startActivity(intent)
        }
    }

    // 선택된 날짜의 식단을 화면에 표시
    private fun displayCurrentDiet() {
        val currentDiet = getCurrentDiet() ?: return
        populateDietTable(currentDiet.menuItems)
    }

    private fun getCurrentDiet() = dietsForSelectedDate.getOrNull(0)

    private fun populateDietTable(menuItems: List<MenuItem>) {
        menuListContainer.removeAllViews()

        var totalCalories = 0
        var totalCarbs = 0
        var totalProtein = 0
        var totalFat = 0

        // 메뉴 아이템을 순차적으로 추가
        menuItems.forEach { item ->
            val row = layoutInflater.inflate(R.layout.menu_item_layout, menuListContainer, false)
            row.findViewById<TextView>(R.id.menu_name_input).text = item.menuName
            row.findViewById<TextView>(R.id.calories_input).text = item.calorie.toString()
            row.findViewById<TextView>(R.id.carbs_input).text = item.carbs.toString()
            row.findViewById<TextView>(R.id.protein_input).text = item.protein.toString()
            row.findViewById<TextView>(R.id.fat_input).text = item.fat.toString()

            totalCalories += item.calorie
            totalCarbs += item.carbs
            totalProtein += item.protein
            totalFat += item.fat

            menuListContainer.addView(row)
        }

        // 총 칼로리, 탄수화물, 단백질, 지방 값 업데이트
        totalCaloriesRow.text = totalCalories.toString()
        totalCarbsRow.text = totalCarbs.toString()
        totalProteinRow.text = totalProtein.toString()
        totalFatRow.text = totalFat.toString()
    }

    private fun showNoDietMessage() {
        menuListContainer.removeAllViews()
        val messageView = TextView(this).apply {
            text = "식단 정보가 없습니다."
            textSize = 16f
            setTextColor(resources.getColor(R.color.black, theme))
        }
        menuListContainer.addView(messageView)

        totalCaloriesRow.text = "0"
        totalCarbsRow.text = "0"
        totalProteinRow.text = "0"
        totalFatRow.text = "0"
    }
}
