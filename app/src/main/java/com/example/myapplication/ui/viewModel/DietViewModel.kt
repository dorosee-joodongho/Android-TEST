package com.example.myapplication.ui.viewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Diet
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.service.DietService
import kotlinx.coroutines.launch
import java.time.LocalDate

class DietViewModel : ViewModel() {

    private val dietService = DietService(RetrofitClient.instance)

    private val _selectedDate = MutableLiveData<LocalDate>(LocalDate.now())
    val selectedDate: LiveData<LocalDate> = _selectedDate

    private val _dietList = MutableLiveData<List<Diet>>() // 전체 식단 데이터
    val dietList: LiveData<List<Diet>> = _dietList

    private val _currentDiet = MutableLiveData<List<Diet>?>() // 선택한 날짜의 식단
    val currentDiet: LiveData<List<Diet>?> = _currentDiet

    private val _monthlyCalories = MutableLiveData<Int>() // 월간 총 칼로리
    val monthlyCalories: LiveData<Int> = _monthlyCalories

    init {
        fetchAllDiets() // 앱 초기화 시 한 번만 호출
    }

    // 전체 식단 데이터 가져오기
    private fun fetchAllDiets() {
        viewModelScope.launch {
            try {
                val diets = dietService.getDietList()
                _dietList.value = diets
                filterDietForSelectedDate() // 초기 날짜 기준으로 필터링
                calculateMonthlyCalories() // 초기 월간 칼로리 계산
            } catch (e: Exception) {
                println("식단 데이터를 가져오는 중 오류 발생: ${e.message}")
                _dietList.value = emptyList() // 오류 시 빈 리스트로 초기화
            }
        }
    }


    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
        filterDietForSelectedDate()
        calculateMonthlyCalories()
    }

    private fun calculateMonthlyCalories() {
        val selectedDate = _selectedDate.value ?: return
        val diets = _dietList.value ?: return

        // 현재 선택된 날짜의 월에 해당하는 식단 총 칼로리 계산
        val totalCalories = diets
            .filter { it.date.year == selectedDate.year && it.date.monthValue == selectedDate.monthValue }
            .sumOf { diet -> diet.menuItems.sumOf { it.calorie } }

        _monthlyCalories.value = totalCalories
    }

    private fun filterDietForSelectedDate() {
        val date = _selectedDate.value ?: return
        val allDiets = _dietList.value ?: return

        // 선택한 날짜의 식단을 필터링
        val filteredDiets = allDiets.filter { it.date == date }

        if (filteredDiets.isEmpty()) {
            _currentDiet.value = null // 선택된 날짜에 식단이 없으면 null로 설정
        } else {
            _currentDiet.value = filteredDiets
        }
    }
}
