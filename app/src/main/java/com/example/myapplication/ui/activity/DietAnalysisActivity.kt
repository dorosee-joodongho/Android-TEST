package com.example.myapplication.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.example.myapplication.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DietAnalysisActivity : AppCompatActivity() {

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

        // 공통 헤더 설정
        val backButton = findViewById<TextView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed() // 뒤로 가기 동작
        }
        backButton.text = "←  식단 분석" // 헤더 제목 변경

        prevWeekButton = findViewById(R.id.button_prev_week)
        nextWeekButton = findViewById(R.id.button_next_week)
        dateRangeText = findViewById(R.id.text_date_range)
        pieChart = findViewById(R.id.pie_chart)
        carbohydrateText = findViewById(R.id.text_carbohydrates_percentage)  // 탄수화물
        proteinText = findViewById(R.id.text_protein_percentage)            // 단백질
        fatText = findViewById(R.id.text_fat_percentage)                    // 지방
        calorieText = findViewById(R.id.text_calories_value)                // 총 칼로리

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
        // 백엔드에서 데이터 가져오는 로직 (Mock 데이터 예제)
        val mockData = mapOf(
            "carbohydrates" to 50,
            "protein" to 30,
            "fat" to 20,
            "calories" to 2000
        )
        displayDietData(mockData)
    }

    private fun displayDietData(data: Map<String, Int>) {
        // 원형 그래프에 데이터 설정
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

        // 텍스트로 퍼센트 표시
        carbohydrateText.text = "${data["carbohydrates"]}%"
        proteinText.text = "${data["protein"]}%"
        fatText.text = "${data["fat"]}%"
        calorieText.text = "${data["calories"]} kcal"
    }
}
