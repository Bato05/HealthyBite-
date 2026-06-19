package com.example.healthybite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.healthybite.model.FoodRepository

/**
 * The factory responsible for creating instances of FoodViewModel.
 *
 * It is used because FoodViewModel needs to receive dependencies
 * (such as the repository) through its constructor, something that the
 * ViewModelProvider cannot do directly without a factory.
 */
class MainViewModelFactory(private val repository: FoodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel class desconocida")
    }
}