package com.example.healthybite.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.healthybite.R
import com.example.healthybite.databinding.ActivityMainBinding
import com.example.healthybite.viewmodel.MainViewModel
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val username = intent.getStringExtra("EXTRA_USERNAME") ?: "Usuario"
        binding.tvUserName.text = username

        setupSpinner()
        setupListeners()
        setupObservers()
    }

    private fun setupSpinner() {
        val categories = resources.getStringArray(R.array.categories_array)

        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories) {
            override fun isEnabled(position: Int): Boolean = position != 0

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val textView = view as TextView
                textView.setTextColor(if (position == 0) Color.GRAY else Color.BLACK)
                return view
            }
        }
        binding.spCategory.adapter = adapter
    }

    private fun setupListeners() {
        binding.btnCalculate.setOnClickListener {
            // Recolectamos la información cruda y se la mandamos directo al ViewModel
            val foodName = binding.etFoodName.text.toString()
            val baseCalories = binding.etBaseCalories.text.toString()
            val isProcessed = binding.cbIsProcessed.isChecked
            val categoryPosition = binding.spCategory.selectedItemPosition
            val category = binding.spCategory.selectedItem?.toString() ?: ""

            viewModel.calculateCalories(foodName, baseCalories, category, categoryPosition, isProcessed)
        }

        // Navegacion hacia el DiaryActivity
        binding.btnOpenDiary.setOnClickListener {
            val intent = Intent(this, DiaryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupObservers() {
        // Observamos el éxito
        viewModel.calculatedFood.observe(this) { foodItem ->
            binding.tvDailyCaloriesTotal.text = "${foodItem.totalCalories.toInt()} kcal"

            val intent = Intent(this, SummaryActivity::class.java).apply {
                putExtra("EXTRA_FOOD_ITEM", foodItem)
            }
            startActivity(intent)
        }

        // Observamos el total acumulado y lo pintamos en el chip
        viewModel.dailyTotalCalories.observe(this) { total ->
            binding.tvDailyCaloriesTotal.text = "$total kcal"
        }

        // Observamos los errores
        viewModel.errorMessage.observe(this) { errorMsg ->
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        // Le decimos al ViewModel que recalcule el total al entrar o volver a esta pantalla
        viewModel.updateDailyTotal()
    }
}