package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
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
    private lateinit var selectedDietName: TextView
    private lateinit var totalCaloriesRow: TextView
    private lateinit var totalCarbsRow: TextView
    private lateinit var totalProteinRow: TextView
    private lateinit var totalFatRow: TextView
    private lateinit var editButton: Button
    private lateinit var deleteButton: Button
    private lateinit var dietAnalysisButton: Button
    private lateinit var prevDietButton: Button
    private lateinit var nextDietButton: Button

    private val viewModel: DietViewModel by viewModels()

    private var currentDietIndex = 0
    private var dietsForSelectedDate: List<Diet> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_view)

        initViews()
        observeViewModel()
        setupListeners()
        viewModel.setSelectedDate(LocalDate.now())

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
        selectedDietName = findViewById(R.id.selected_diet_name)
        totalCaloriesRow = findViewById(R.id.total_calories_row)
        totalCarbsRow = findViewById(R.id.total_carbs_row)
        totalProteinRow = findViewById(R.id.total_protein_row)
        totalFatRow = findViewById(R.id.total_fat_row)
//        editButton = findViewById(R.id.edit_button)
//        deleteButton = findViewById(R.id.delete_button)
        dietAnalysisButton = findViewById(R.id.diet_analysis_button)
        prevDietButton = findViewById(R.id.prev_diet_button)
        nextDietButton = findViewById(R.id.next_diet_button)
    }

    private fun observeViewModel() {
        viewModel.currentDiet.observe(this, Observer { diets ->
            dietsForSelectedDate = diets ?: emptyList()
            if (dietsForSelectedDate.isNotEmpty()) {
                currentDietIndex = 0
                displayCurrentDiet()
            } else {
                showNoDietMessage()
            }
        })

        viewModel.monthlyCalories.observe(this, Observer { totalCalories ->
            totalMonthlyCaloriesText.text = "총 칼로리: $totalCalories kcal"
        })
    }

    private fun setupListeners() {
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDay = LocalDate.of(year, month + 1, dayOfMonth)
            viewModel.setSelectedDate(selectedDay)
        }

        prevDietButton.setOnClickListener {
            if (dietsForSelectedDate.isNotEmpty()) {
                currentDietIndex = (currentDietIndex - 1 + dietsForSelectedDate.size) % dietsForSelectedDate.size
                displayCurrentDiet()
            }
        }

        nextDietButton.setOnClickListener {
            if (dietsForSelectedDate.isNotEmpty()) {
                currentDietIndex = (currentDietIndex + 1) % dietsForSelectedDate.size
                displayCurrentDiet()
            }
        }

//        editButton.setOnClickListener {
//            val diet = getCurrentDiet() ?: return@setOnClickListener
//            viewModel.updateDiet(diet) { success ->
//                showToast(if (success) "식단이 수정되었습니다." else "수정에 실패하였습니다.")
//            }
//        }
//
//        deleteButton.setOnClickListener {
//            val diet = getCurrentDiet() ?: return@setOnClickListener
//            viewModel.deleteDiet(diet.id!!) { success ->
//                showToast(if (success) "식단이 삭제되었습니다." else "삭제에 실패하였습니다.")
//            }
//        }

        dietAnalysisButton.setOnClickListener {
            val intent = Intent(this, DietAnalysisActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayCurrentDiet() {
        val currentDiet = getCurrentDiet() ?: return
        populateDietTable(currentDiet.menuItems)
        selectedDietName.text = currentDiet.dietName
    }

    private fun getCurrentDiet() = dietsForSelectedDate.getOrNull(currentDietIndex)

    private fun populateDietTable(menuItems: List<MenuItem>) {
        menuListContainer.removeAllViews()

        var totalCalories = 0
        var totalCarbs = 0
        var totalProtein = 0
        var totalFat = 0

        menuItems.forEach { item ->
            val row = LayoutInflater.from(this).inflate(R.layout.menu_item_layout, menuListContainer, false)
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

        selectedDietName.text = "식단 이름"
        totalCaloriesRow.text = "0"
        totalCarbsRow.text = "0"
        totalProteinRow.text = "0"
        totalFatRow.text = "0"
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
