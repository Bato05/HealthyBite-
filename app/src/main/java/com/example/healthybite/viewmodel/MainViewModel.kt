package com.example.healthybite.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthybite.model.FoodItem
import com.example.healthybite.model.FoodRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FoodRepository(application)

    private val _calculatedFood = MutableLiveData<FoodItem>()
    val calculatedFood: LiveData<FoodItem> get() = _calculatedFood

    // Nuevo estado para manejar los errores de validación
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun calculateCalories(name: String, baseCaloriesStr: String, category: String, categoryPosition: Int, isProcessed: Boolean) {
        // 1. Validaciones en el ViewModel (Responsabilidad correcta)
        if (name.isBlank() || baseCaloriesStr.isBlank()) {
            _errorMessage.value = "Por favor, completa todos los campos"
            return
        }

        if (categoryPosition == 0) {
            _errorMessage.value = "Por favor, selecciona una categoría válida"
            return
        }

        // 2. Lógica de negocio matemática
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

        // 3. Persistencia
        repository.saveLastFoodName(foodItem.name)

        // 4. Éxito
        _calculatedFood.value = foodItem
    }
}