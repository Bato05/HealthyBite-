package com.example.healthybite.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.healthybite.databinding.ItemFoodBinding
import com.example.healthybite.model.FoodItem

class FoodAdapter(private var foodList: List<FoodItem>) : RecyclerView.Adapter<FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        // Inflamos el XML de la fila usando la clase de View Binding
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        holder.render(food)
    }

    override fun getItemCount(): Int = foodList.size

    fun updateData(newList: List<FoodItem>) {
        foodList = newList
        notifyDataSetChanged()
    }
}