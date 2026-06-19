package com.example.healthybite.view

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.healthybite.databinding.ActivitySummaryBinding
import com.example.healthybite.model.FoodItem
import com.example.healthybite.viewmodel.SummaryViewModel

class SummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySummaryBinding
    private val viewModel: SummaryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupObservers()
        getDataAndLoadViewModel()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun getDataAndLoadViewModel() {
        val foodItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EXTRA_FOOD_ITEM", FoodItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("EXTRA_FOOD_ITEM") as? FoodItem
        }

        // Le enviamos el objeto crudo al ViewModel
        foodItem?.let { viewModel.loadFoodData(it) }
    }

    private fun setupObservers() {
        // La vista solo obedece
        viewModel.foodName.observe(this) { binding.tvSummaryName.text = it }
        viewModel.category.observe(this) { binding.tvSummaryCategory.text = it }
        viewModel.baseCalories.observe(this) { binding.tvSummaryBaseCalories.text = it }
        viewModel.totalCalories.observe(this) { binding.tvSummaryTotalCalories.text = it }

        viewModel.processedTextResId.observe(this) {
            binding.tvSummaryProcessed.text = getString(it)
        }
        viewModel.processedColorResId.observe(this) { colorRes ->
            binding.tvSummaryProcessed.setTextColor(ContextCompat.getColor(this, colorRes))
        }
    }
}