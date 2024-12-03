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
        fetchAllDiets()
    }

    // 전체 식단 데이터 가져오기
    private fun fetchAllDiets() {
        viewModelScope.launch {
            val diets = dietService.getDietList()
            _dietList.value = diets
            if (diets.isNotEmpty()) {
                filterDietForSelectedDate()
                calculateMonthlyCalories()
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

        val totalCalories = diets
            .filter { it.getDateAsLocalDate().year == selectedDate.year && it.getDateAsLocalDate().monthValue == selectedDate.monthValue }
            .sumOf { diet -> diet.menuItems.sumOf { it.calorie } }

        _monthlyCalories.value = totalCalories
    }

    private fun filterDietForSelectedDate() {
        val date = _selectedDate.value ?: return
        val allDiets = _dietList.value ?: return

        val filteredDiets = allDiets.filter { it.getDateAsLocalDate() == date }

        _currentDiet.value = if (filteredDiets.isEmpty()) null else filteredDiets
    }
}
