package com.example.healthybite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthybite.R
import com.example.healthybite.model.FoodItem

class SummaryViewModel : ViewModel() {

    // Estados para cada elemento de la interfaz
    private val _foodName = MutableLiveData<String>()
    val foodName: LiveData<String> get() = _foodName

    private val _category = MutableLiveData<String>()
    val category: LiveData<String> get() = _category

    private val _baseCalories = MutableLiveData<String>()
    val baseCalories: LiveData<String> get() = _baseCalories

    // Guardamos las referencias a los recursos (Int) para no importar dependencias de la vista aquí
    private val _processedTextResId = MutableLiveData<Int>()
    val processedTextResId: LiveData<Int> get() = _processedTextResId

    private val _processedColorResId = MutableLiveData<Int>()
    val processedColorResId: LiveData<Int> get() = _processedColorResId

    private val _totalCalories = MutableLiveData<String>()
    val totalCalories: LiveData<String> get() = _totalCalories

    // Función principal que la vista llamará al recibir el intent
    fun loadFoodData(foodItem: FoodItem) {
        _foodName.value = foodItem.name
        _category.value = "Categoría: ${foodItem.category}"
        _baseCalories.value = foodItem.baseCalories.toString()

        // Lógica de presentación pura en el ViewModel
        if (foodItem.isProcessed) {
            _processedTextResId.value = R.string.yes_10
            _processedColorResId.value = R.color.orange
        } else {
            _processedTextResId.value = R.string.no_10
            _processedColorResId.value = R.color.green
        }

        _totalCalories.value = "${foodItem.totalCalories.toInt()} kcal"
    }
}