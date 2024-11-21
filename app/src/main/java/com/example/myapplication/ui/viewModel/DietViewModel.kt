package com.example.myapplication.ui.viewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Diet
import com.example.myapplication.service.DietService
import java.time.LocalDate

class DietViewModel : ViewModel() {

    private val dietService = DietService()

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

    private fun fetchAllDiets() {
        dietService.getDietList { diets ->
            _dietList.value = diets
            filterDietForSelectedDate() // 초기 선택 날짜에 맞는 데이터를 설정
            calculateMonthlyCalories() // 월간 총 칼로리 계산
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
        _currentDiet.value = allDiets.filter { it.date == date }
    }

    fun deleteDiet(dietId: Long, callback: (Boolean) -> Unit) {
        dietService.deleteDiet(dietId) { success ->
            if (success) {
                // 서버에서 삭제 성공 시 로컬 데이터 업데이트
                _dietList.value = _dietList.value?.filter { it.id != dietId }
                filterDietForSelectedDate()
            }
            callback(success)
        }
    }

    fun updateDiet(diet: Diet, callback: (Boolean) -> Unit) {
        dietService.updateDiet(diet) { updatedDiet ->
            if (updatedDiet != null) {
                // 서버 업데이트 성공 시 로컬 데이터 수정
                _dietList.value = _dietList.value?.map {
                    if (it.id == updatedDiet.id) updatedDiet else it
                }
                filterDietForSelectedDate()
                callback(true)
            } else {
                callback(false)
            }
        }
    }
}
