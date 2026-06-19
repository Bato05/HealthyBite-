package com.example.healthybite.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthybite.R
import com.example.healthybite.databinding.ActivityDiaryBinding
import com.example.healthybite.view.adapter.FoodAdapter
import com.example.healthybite.viewmodel.DiaryViewModel

class DiaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiaryBinding
    private val viewModel: DiaryViewModel by viewModels()
    private lateinit var adapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuramos RecyclerView
        adapter = FoodAdapter(emptyList())
        binding.rvFoodDiary.layoutManager = LinearLayoutManager(this)
        binding.rvFoodDiary.adapter = adapter

        // Solicitamos los datos al ViewModel
        viewModel.fetchDiaryData()
        setupObservers()

        binding.btnBackToMain.setOnClickListener {
            finish()
        }
    }

    private fun setupObservers() {
        // Observamos el último alimento (para la tarjeta)
        viewModel.lastFood.observe(this) { foodName ->
            binding.tvLastFoodSaved.text = foodName
        }

        // Observamos la lista completa (para el RecyclerView)
        viewModel.foodList.observe(this) { list ->
            adapter.updateData(list)
        }
    }
}