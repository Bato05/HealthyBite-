package com.example.healthybite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthybite.model.FoodItem
import com.example.healthybite.model.FoodRepository
import com.example.healthybite.view.Event

class MainViewModel(private val repository: FoodRepository) : ViewModel() {

    private val _calculatedFood = MutableLiveData<Event<FoodItem>>()
    val calculatedFood: LiveData<Event<FoodItem>> get() = _calculatedFood

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _dailyTotalCalories = MutableLiveData<Int>()
    val dailyTotalCalories: LiveData<Int> get() = _dailyTotalCalories

    fun calculateCalories(name: String, baseCaloriesStr: String, category: String, categoryPosition: Int, isProcessed: Boolean) {
        if (name.isBlank() || baseCaloriesStr.isBlank() || categoryPosition == 0) {
            _errorMessage.value = "Por favor, completa todos los campos"
        }
        else {
            val baseCalories = baseCaloriesStr.toIntOrNull() ?: 0
            val multiplier = if (isProcessed) 1.10 else 1.0
            val total = baseCalories * multiplier

            val foodItem = FoodItem(
                name = name,
                baseCalories = baseCalories,
                category = category,
                isProcessed = isProcessed,
                totalCalories = total
            )

            repository.saveFood(foodItem)
            _calculatedFood.value = Event(foodItem)
        }
    }

    fun updateDailyTotal() {
        val total = repository.getSessionFoodList().sumOf { it.totalCalories.toInt() }
        _dailyTotalCalories.value = total
    }
}