package com.example.healthybite.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.healthybite.databinding.ItemFoodBinding
import com.example.healthybite.model.FoodItem

// 1. Recibe el Binding y le pasa la vista raíz (binding.root) al padre
class FoodViewHolder(private val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {

    // 2. Funcion "binding" o "render" para renderizar los itemFood
    fun render(food: FoodItem) {
        binding.tvItemName.text = food.name
        binding.tvItemCategory.text = food.category
        binding.tvItemCalories.text = "${food.totalCalories.toInt()} kcal"
    }
}