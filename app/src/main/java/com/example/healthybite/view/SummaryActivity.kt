package com.example.healthybite.view

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.healthybite.R
import com.example.healthybite.databinding.ActivitySummaryBinding
import com.example.healthybite.model.FoodItem

class SummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializamos el binding correctamente
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recuperamos los datos
        getData()

        // Configuramos el botón de volver para cerrar esta pantalla
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun getData() {
        val foodItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EXTRA_FOOD_ITEM", FoodItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("EXTRA_FOOD_ITEM") as? FoodItem
        }

        foodItem?.let {
            // Seteamos los textos en la interfaz
            binding.tvSummaryName.text = it.name
            binding.tvSummaryCategory.text = "Categoría: ${it.category}"
            binding.tvSummaryBaseCalories.text = it.baseCalories.toString()

            // Lógica visual para mostrar si es procesado o no
            if (it.isProcessed) {
                binding.tvSummaryProcessed.text = getString(R.string.yes_10)
                binding.tvSummaryProcessed.setTextColor(ContextCompat.getColor(this, R.color.orange))
            } else {
                binding.tvSummaryProcessed.text = getString(R.string.no_10)
                binding.tvSummaryProcessed.setTextColor(ContextCompat.getColor(this, R.color.green))
            }

            binding.tvSummaryTotalCalories.text = "${it.totalCalories.toInt()} kcal"
        }
    }
}