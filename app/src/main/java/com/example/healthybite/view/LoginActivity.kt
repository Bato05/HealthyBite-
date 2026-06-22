package com.example.healthybite.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.healthybite.databinding.ActivityLoginBinding
import com.example.healthybite.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    // Instanciamos el ViewModel
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            // Le pasamos el dato al ViewModel
            val username = binding.etUsername.text.toString().trim()
            viewModel.login(username)
        }
    }

    private fun setupObservers() {
        // Observamos si debemos navegar
        viewModel.navigateToMain.observe(this) { username ->
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("EXTRA_USERNAME", username)
            }
            startActivity(intent)
            finish()
        }

        // Observamos si hay que mostrar un error
        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}