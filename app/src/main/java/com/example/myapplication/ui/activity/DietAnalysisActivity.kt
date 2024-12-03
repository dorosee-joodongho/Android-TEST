package com.example.myapplication.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.example.myapplication.R
import com.example.myapplication.data.MenuItem
import com.example.myapplication.ui.viewModel.DietViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DietAnalysisActivity : AppCompatActivity() {
    private lateinit var dietViewModel: DietViewModel
    private lateinit var prevWeekButton: Button
    private lateinit var nextWeekButton: Button
    private lateinit var dateRangeText: TextView
    private lateinit var pieChart: PieChart
    private lateinit var carbohydrateText: TextView
    private lateinit var proteinText: TextView
    private lateinit var fatText: TextView
    private lateinit var calorieText: TextView

    private var startDate: LocalDate = LocalDate.now().minusDays(7)
    private var endDate: LocalDate = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_analysis)

        dietViewModel = ViewModelProvider(this)[DietViewModel::class.java]

        val backButton = findViewById<TextView>(R.id.backButton)
        backButton.setOnClickListener { onBackPressed() }
        backButton.text = "←  식단 분석"

        prevWeekButton = findViewById(R.id.button_prev_week)
        nextWeekButton = findViewById(R.id.button_next_week)
        dateRangeText = findViewById(R.id.text_date_range)
        pieChart = findViewById(R.id.pie_chart)
        carbohydrateText = findViewById(R.id.text_carbohydrates_percentage)
        proteinText = findViewById(R.id.text_protein_percentage)
        fatText = findViewById(R.id.text_fat_percentage)
        calorieText = findViewById(R.id.text_calories_value)

        updateDateRange()
        setupListeners()
        fetchDietDataForWeek()
    }

    private fun setupListeners() {
        prevWeekButton.setOnClickListener {
            moveToPreviousWeek()
        }

        nextWeekButton.setOnClickListener {
            moveToNextWeek()
        }
    }

    private fun moveToPreviousWeek() {
        startDate = startDate.minusWeeks(1)
        endDate = endDate.minusWeeks(1)
        updateDateRange()
        fetchDietDataForWeek()
    }

    private fun moveToNextWeek() {
        startDate = startDate.plusWeeks(1)
        endDate = endDate.plusWeeks(1)
        updateDateRange()
        fetchDietDataForWeek()
    }

    private fun updateDateRange() {
        val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")
        dateRangeText.text = "${startDate.format(formatter)} ~ ${endDate.format(formatter)}"
    }

    private fun fetchDietDataForWeek() {
        val weeklyDiet = dietViewModel.dietList.value?.filter { diet ->
            val dietDate = diet.getDateAsLocalDate()
            dietDate in startDate..endDate
        }.orEmpty()

        val aggregatedData = weeklyDiet.flatMap { it.menuItems }.fold(
            MenuItem("", 0, 0, 0, 0)
        ) { acc, item ->
            acc.apply {
                calorie += item.calorie
                carbs += item.carbs
                protein += item.protein
                fat += item.fat
            }
        }

        val totalNutrients = aggregatedData.carbs + aggregatedData.protein + aggregatedData.fat
        val percentages = if (totalNutrients > 0) {
            mapOf(
                "carbohydrates" to aggregatedData.carbs * 100 / totalNutrients,
                "protein" to aggregatedData.protein * 100 / totalNutrients,
                "fat" to aggregatedData.fat * 100 / totalNutrients,
                "calories" to aggregatedData.calorie
            )
        } else {
            mapOf("carbohydrates" to 0, "protein" to 0, "fat" to 0, "calories" to 0)
        }
        displayDietData(percentages)
    }


    // 원형 그래프에 데이터 설정
    private fun displayDietData(data: Map<String, Int>) {
        val entries = listOf(
            PieEntry(data["carbohydrates"]!!.toFloat(), "탄수화물"),
            PieEntry(data["protein"]!!.toFloat(), "단백질"),
            PieEntry(data["fat"]!!.toFloat(), "지방")
        )
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextSize = 16f

        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.invalidate() // 그래프 새로고침

        // 텍스트로 퍼센트 및 칼로리 표시
        carbohydrateText.text = "${data["carbohydrates"]}%"
        proteinText.text = "${data["protein"]}%"
        fatText.text = "${data["fat"]}%"
        calorieText.text = "${data["calories"]} kcal" // 칼로리 합산 값 표시
    }
}
