package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CalendarView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.data.Diet
import com.example.myapplication.data.MenuItem
import com.example.myapplication.service.DietService
import java.time.LocalDate

class DietViewActivity : AppCompatActivity() {

    private lateinit var totalCaloriesText: TextView
    private lateinit var dietAnalysisButton: Button
    private lateinit var calendarView: CalendarView
    private lateinit var prevDietButton: Button
    private lateinit var nextDietButton: Button
    private lateinit var selectedDietName: TextView
    private lateinit var menuListContainer: LinearLayout
    private lateinit var totalCaloriesRow: TextView
    private lateinit var totalCarbsRow: TextView
    private lateinit var totalProteinRow: TextView
    private lateinit var totalFatRow: TextView
    private lateinit var editButton: Button
    private lateinit var deleteButton: Button

    private val dietActivity = DietService()

    private var selectedDate: LocalDate = LocalDate.now()
    private var currentDietIndex = 0
    private var dietList: List<Diet> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_view)

        initViews()
        setupListeners()
        fetchDietDataForMonth(selectedDate)

        // 공통 헤더 설정
        val backButton = findViewById<TextView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed() // 뒤로 가기 동작
        }
        backButton.text = "←  내 식단" // 헤더 제목 변경
    }

    private fun initViews() {
        totalCaloriesText = findViewById(R.id.total_calories_text)
        dietAnalysisButton = findViewById(R.id.diet_analysis_button)
        calendarView = findViewById(R.id.calendar_view)
        prevDietButton = findViewById(R.id.prev_diet_button)
        nextDietButton = findViewById(R.id.next_diet_button)
        selectedDietName = findViewById(R.id.selected_diet_name)
        menuListContainer = findViewById(R.id.menu_list_container)
        totalCaloriesRow = findViewById(R.id.total_calories_row)
        totalCarbsRow = findViewById(R.id.total_carbs_row)
        totalProteinRow = findViewById(R.id.total_protein_row)
        totalFatRow = findViewById(R.id.total_fat_row)
        editButton = findViewById(R.id.edit_button)
        deleteButton = findViewById(R.id.delete_button)
    }

    private fun setupListeners() {
        dietAnalysisButton.setOnClickListener { // 식단 분석 버튼
            val intent = Intent(this, DietAnalysisActivity::class.java)
            startActivity(intent)
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDay = LocalDate.of(year, month + 1, dayOfMonth)
            fetchDietDataForDay(selectedDay)
        }

        prevDietButton.setOnClickListener {
            if (currentDietIndex > 0) {
                currentDietIndex--
                updateDietDetails()
            }
        }

        nextDietButton.setOnClickListener {
            if (currentDietIndex < dietList.size - 1) {
                currentDietIndex++
                updateDietDetails()
            }
        }

        editButton.setOnClickListener { // 수정 하기 버튼
//            val intent = Intent(this, AddDietActivity::class.java).apply {
//                putExtra("dietData", dietList[currentDietIndex])
//            }
//            startActivity(intent)
        }

        deleteButton.setOnClickListener {
            deleteDiet(dietList[currentDietIndex].id)
        }
    }

    private fun updateDietDetails() {
        val diet = dietList[currentDietIndex]
        selectedDietName.text = diet.name
        populateDietTable(diet.menuItems)
    }

//    데이터 로딩 및 API 통신
    private fun fetchDietDataForMonth(date: LocalDate) {
        dietActivity.getDietDataForMonth(date.year, date.monthValue) { diets ->
            dietList = diets
            val total = diets.sumOf { it.menuItems.sumOf { menu -> menu.calories } }
            totalCaloriesText.text = "총 칼로리: ${total} kcal"

            if (diets.isNotEmpty()) {
                currentDietIndex = 0
                updateDietDetails()
            }
        }
    }

    private fun fetchDietDataForDay(date: LocalDate) {
        dietActivity.getDietDataForDay(date) { diet ->
            if (diet != null) {
                dietList = listOf(diet)
                currentDietIndex = 0
                updateDietDetails()
            } else {
                Toast.makeText(this, "해당 날짜에 식단 정보가 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun populateDietTable(menuItems: List<MenuItem>) {
        menuListContainer.removeAllViews()
        var totalCalories = 0
        var totalCarbs = 0
        var totalProtein = 0
        var totalFat = 0

        menuItems.forEach { item ->
            val row = LayoutInflater.from(this).inflate(R.layout.menu_item_layout, menuListContainer, false)
            row.findViewById<TextView>(R.id.menu_name_input).text = item.name
            row.findViewById<TextView>(R.id.calories_input).text = item.calories.toString()
            row.findViewById<TextView>(R.id.carbs_input).text = item.carbs.toString()
            row.findViewById<TextView>(R.id.protein_input).text = item.protein.toString()
            row.findViewById<TextView>(R.id.fat_input).text = item.fat.toString()

            totalCalories += item.calories
            totalCarbs += item.carbs
            totalProtein += item.protein
            totalFat += item.fat

            menuListContainer.addView(row)
        }

        totalCaloriesRow.text = totalCalories.toString()
        totalCarbsRow.text = totalCarbs.toString()
        totalProteinRow.text = totalProtein.toString()
        totalFatRow.text = totalFat.toString()
    }

    private fun deleteDiet(dietId: Int) {
        dietActivity.deleteDiet(dietId) { success ->
            if (success) {
                Toast.makeText(this, "식단이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                fetchDietDataForMonth(selectedDate)
            } else {
                Toast.makeText(this, "식단 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
