package com.example.healthybite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    // Estado para avisar a la vista que puede navegar
    private val _navigateToMain = MutableLiveData<String>()
    val navigateToMain: LiveData<String> get() = _navigateToMain

    // Estado para mostrar errores
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun login(username: String) {
        if (username.isNotBlank()) {
            // Lógica exitosa: enviamos el nombre para que la vista navegue
            _navigateToMain.value = username
        } else {
            // Lógica fallida: enviamos un error
            _errorMessage.value = "Por favor, ingresa tu nombre"
        }
    }
}