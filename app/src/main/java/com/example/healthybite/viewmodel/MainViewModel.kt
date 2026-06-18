package com.example.healthybite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthybite.model.FoodItem

class MainViewModel : ViewModel() {

    // LiveData nos permite crear un "estado reactivo".
    // La vista observará esta variable y se actualizará automáticamente cuando cambie.
    private val _calculatedFood = MutableLiveData<FoodItem>()
    val calculatedFood: LiveData<FoodItem> get() = _calculatedFood

    fun calculateCalories(name: String, baseCaloriesStr: String, category: String, isProcessed: Boolean) {
        // Validamos que las calorías no estén vacías
        val baseCalories = baseCaloriesStr.toIntOrNull() ?: 0

        // Aplicamos el recargo del 10% si es procesado
        val multiplier = if (isProcessed) 1.10 else 1.0
        val total = baseCalories * multiplier

        // Instanciamos el modelo que ya tenías creado
        val foodItem = FoodItem(
            name = name,
            baseCalories = baseCalories,
            category = category,
            isProcessed = isProcessed,
            totalCalories = total
        )

        // Actualizamos el estado
        _calculatedFood.value = foodItem
    }
}