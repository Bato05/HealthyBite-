package com.example.healthybite.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthybite.databinding.ActivityMainBinding
import com.example.healthybite.model.FoodItem
import com.example.healthybite.viewmodel.MainViewModel
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    // 1. Declaramos la variable de View Binding
    private lateinit var binding: ActivityMainBinding
    // 2. Instanciamos el ViewModel
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 2. PRIMERO inicializamos View Binding inflando el layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 3. DESPUÉS usamos el binding para ajustar los insets de la pantalla
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 4. Finalmente llamamos a tus funciones
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.btnCalculate.setOnClickListener {
            val foodName = binding.etFoodName.text.toString()
            val baseCalories = binding.etBaseCalories.text.toString()
            val isProcessed = binding.cbIsProcessed.isChecked
            val category = binding.spCategory.selectedItem?.toString() ?: "General"

            // Validamos que el nombre no esté vacío antes de enviar al ViewModel
            if (foodName.isNotBlank() && baseCalories.isNotBlank()) {
                viewModel.calculateCalories(foodName, baseCalories, category, isProcessed)
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupObservers() {
        viewModel.calculatedFood.observe(this) { foodItem ->
            // Actualizamos la interfaz
            binding.tvDailyCaloriesTotal.text = "${foodItem.totalCalories.toInt()} kcal"
            Toast.makeText(this, "${foodItem.name} guardado con éxito", Toast.LENGTH_SHORT).show()

            // Creamos el Intent para viajar a la siguiente pantalla
            val intent = Intent(this, SummaryActivity::class.java).apply {
                // Le pasamos el objeto completo a la siguiente pantalla
                putExtra("EXTRA_FOOD_ITEM", foodItem)
            }
            startActivity(intent)
        }
    }
}