package com.example.healthybite.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.healthybite.model.FoodItem
import com.example.healthybite.model.FoodRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FoodRepository(application)

    private val _calculatedFood = MutableLiveData<FoodItem>()
    val calculatedFood: LiveData<FoodItem> get() = _calculatedFood

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    // Guarda el acumulado de las calorias por alimento
    private val _dailyTotalCalories = MutableLiveData<Int>()

    val dailyTotalCalories: LiveData<Int> get() = _dailyTotalCalories

    fun calculateCalories(name: String, baseCaloriesStr: String, category: String, categoryPosition: Int, isProcessed: Boolean) {
        if (name.isBlank() || baseCaloriesStr.isBlank() || categoryPosition == 0) {
            _errorMessage.value = "Por favor, completa todos los campos"
            return
        }

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

        // Aquí usamos la función actualizada del Repositorio
        repository.saveFood(foodItem)

        _calculatedFood.value = foodItem
    }

    fun updateDailyTotal() {
        // Obtenemos la lista de la sesión y sumamos las calorías totales
        val total = repository.getSessionFoodList().sumOf { it.totalCalories.toInt() }
        _dailyTotalCalories.value = total
    }
}