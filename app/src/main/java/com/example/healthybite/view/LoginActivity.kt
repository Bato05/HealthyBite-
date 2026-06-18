package com.example.healthybite.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.healthybite.R
import com.example.healthybite.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

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

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()

            if (username.isNotEmpty()) {
                // Viajamos a MainActivity pasándole el nombre de usuario
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("EXTRA_USERNAME", username)
                }
                startActivity(intent)

                // IMPORTANTE: Llamamos a finish() para cerrar la pantalla de Login
                // Así, si el usuario toca la flecha de volver, sale de la app en lugar de volver al login
                finish()
            } else {
                Toast.makeText(this, "Por favor, ingresa tu nombre", Toast.LENGTH_SHORT).show()
            }
        }
    }
}