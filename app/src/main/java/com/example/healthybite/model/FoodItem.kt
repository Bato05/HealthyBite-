package com.example.healthybite.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data class representing a food item logged by the user.
 * Implements Parcelable to allow safe and efficient data transfer between Activities.
 */

@Parcelize
data class FoodItem(
    val name: String, // Nombre del alimento
    val baseCalories: Int, // Calorías base ingresadas (numérico)
    val category: String, // Categoría elegida en el Spinner (Breakfast, Lunch, Dinner, Snack)
    val isProcessed: Boolean, // CheckBox: true si es procesado, false si no lo es
    var totalCalories: Double = 0.0 // Total calculado (Base + 10% recargo si aplica)
) : Parcelable
