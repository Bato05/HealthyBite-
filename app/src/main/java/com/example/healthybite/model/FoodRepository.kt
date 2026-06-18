package com.example.healthybite.model

import android.content.Context
import android.content.SharedPreferences

/**
 * FoodRepository handles the data layer for the MVVM architecture.
 * It uses SharedPreferences to save and retrieve data persistently on the device.
 *
 * Methods:
 * - saveLastFoodName(name): Saves the food name locally in the background.
 * - getLastFoodName(): Retrieves the saved name, returning "Ninguno" if empty.
 */

class FoodRepository(context: Context) {

    // 1. Instanciamos SharedPreferences en modo privado
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("HealthyBitePrefs", Context.MODE_PRIVATE)

    // 2. Función para guardar información (ejemplo: guardar si un alimento fue registrado)
    fun saveLastFoodName(foodName: String) {
        sharedPreferences.edit().putString("LAST_FOOD_NAME", foodName).apply()
    }

    // 3. Función para recuperar la información
    fun getLastFoodName(): String {
        return sharedPreferences.getString("LAST_FOOD_NAME", "Ninguno") ?: "Ninguno"
    }

    // Nota: Más adelante podemos agregar funciones para guardar la lista completa
    // de FoodItem usando JSON, o guardar el total de calorías.
}