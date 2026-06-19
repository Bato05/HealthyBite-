package com.example.healthybite.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.healthybite.model.FoodItem
import com.example.healthybite.model.FoodRepository

class DiaryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FoodRepository(application)

    private val _lastFood = MutableLiveData<String>()
    val lastFood: LiveData<String> get() = _lastFood

    private val _foodList = MutableLiveData<List<FoodItem>>()
    val foodList: LiveData<List<FoodItem>> get() = _foodList

    fun fetchDiaryData() {
        _lastFood.value = repository.getLastFoodName()
        _foodList.value = repository.getSessionFoodList()
    }
}