package com.example.healthybite.model

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * FoodRepository handles the data layer for the MVVM architecture.
 * It uses SharedPreferences to save and retrieve data persistently on the device.
 *
 * Methods:
 * - saveFood(foodItem): Saves the food locally in the session.
 * - getLastFoodName(): Retrieves the saved name, returning "Ninguno" if empty.
 * - getSessionFoodList(): Returns the list of FoodItems
 */

class FoodRepository(context: Context) {

    // 1. Instanciamos SharedPreferences en modo privado
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("HealthyBitePrefs", Context.MODE_PRIVATE)

    // Lista en memoria para la sesión actual
    companion object {
        private val sessionFoodList = mutableListOf<FoodItem>()
    }

    // 2. Función para guardar el FoodItem
    // Guarda en SharedPreferences y en la lista de sesión al mismo tiempo
    fun saveFood(foodItem: FoodItem) {
        sharedPreferences.edit { putString("LAST_FOOD_NAME", foodItem.name) }
        sessionFoodList.add(foodItem)
    }

    // 3. Función para recuperar la información
    fun getLastFoodName(): String {
        return sharedPreferences.getString("LAST_FOOD_NAME", "Ninguno") ?: "Ninguno"
    }

    // 4. Retorna la lista de comidas o FoodItems
    fun getSessionFoodList(): List<FoodItem> {
        return sessionFoodList.toList()
    }
}