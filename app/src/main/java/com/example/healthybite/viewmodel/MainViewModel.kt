package com.example.healthybite.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthybite.model.FoodItem
import com.example.healthybite.model.FoodRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _calculatedFood = MutableLiveData<FoodItem>()
    val calculatedFood: LiveData<FoodItem> get() = _calculatedFood

    // Instanciamos tu repositorio
    private val repository = FoodRepository(application)

    fun calculateCalories(name: String, baseCaloriesStr: String, category: String, isProcessed: Boolean) {
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

        // Guardamos el nombre del último alimento ingresado
        repository.saveLastFoodName(foodItem.name)

        _calculatedFood.value = foodItem
    }
}