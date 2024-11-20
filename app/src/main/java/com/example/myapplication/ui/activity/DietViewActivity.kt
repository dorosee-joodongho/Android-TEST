package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.data.MenuItem
import com.example.myapplication.ui.viewModel.DietViewModel
import java.time.LocalDate

class DietViewActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var totalMonthlyCaloriesText: TextView
    private lateinit var menuListContainer: LinearLayout
    private lateinit var selectedDietName: TextView
    private lateinit var totalCaloriesRow: TextView
    private lateinit var totalCarbsRow: TextView
    private lateinit var totalProteinRow: TextView
    private lateinit var totalFatRow: TextView
    private lateinit var editButton: Button
    private lateinit var deleteButton: Button
    private lateinit var dietAnalysisButton: Button

    private val viewModel: DietViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_view)

        initViews()
        observeViewModel()
        setupListeners()
        viewModel.setSelectedDate(LocalDate.now())

        // 공통 헤더 설정
        val backButton = findViewById<TextView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed() // 뒤로 가기 동작
        }
        backButton.text = "←  내 식단" // 헤더 제목 변경
    }

    private fun initViews() {
        calendarView = findViewById(R.id.calendar_view)
        totalMonthlyCaloriesText = findViewById(R.id.total_calories_text)
        menuListContainer = findViewById(R.id.menu_list_container)
        selectedDietName = findViewById(R.id.selected_diet_name)
        totalCaloriesRow = findViewById(R.id.total_calories_row)
        totalCarbsRow = findViewById(R.id.total_carbs_row)
        totalProteinRow = findViewById(R.id.total_protein_row)
        totalFatRow = findViewById(R.id.total_fat_row)
        editButton = findViewById(R.id.edit_button)
        deleteButton = findViewById(R.id.delete_button)
        dietAnalysisButton = findViewById(R.id.diet_analysis_button)
    }

    private fun observeViewModel() {
        viewModel.currentDiet.observe(this, Observer { diet ->
            if (diet != null) {
                populateDietTable(diet.menuItems)
                selectedDietName.text = diet.name
            } else {
                showNoDietMessage()
            }

            // 월간 총 칼로리
            viewModel.monthlyCalories.observe(this, Observer { totalCalories ->
                totalMonthlyCaloriesText.text = "총 칼로리: $totalCalories kcal"
            })
        })
    }

    private fun setupListeners() {
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDay = LocalDate.of(year, month + 1, dayOfMonth)
            viewModel.setSelectedDate(selectedDay)
        }

        editButton.setOnClickListener {
            viewModel.currentDiet.value?.let { diet ->
                viewModel.updateDiet(diet) { success ->
                    if (success) {
                        showToast("식단이 수정되었습니다.")
                    } else {
                        showToast("수정에 실패하였습니다.")
                    }
                }
            } ?: showToast("수정할 식단 정보가 없습니다.")
        }

        deleteButton.setOnClickListener {
            viewModel.currentDiet.value?.let { diet ->
                viewModel.deleteDiet(diet.id!!) { success ->
                    if (success) {
                        showToast("식단이 삭제되었습니다.")
                    } else {
                        showToast("식단 삭제에 실패했습니다.")
                    }
                }
            } ?: showToast("삭제할 식단 정보가 없습니다.")
        }

        dietAnalysisButton.setOnClickListener {
            val intent = Intent(this, DietAnalysisActivity::class.java)
            startActivity(intent)
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
