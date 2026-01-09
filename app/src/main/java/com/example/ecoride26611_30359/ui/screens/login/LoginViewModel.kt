package com.example.ecoride26611_30359.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    // Estado do Email
    var email by mutableStateOf("")
        private set

    // Estado da Password
    var password by mutableStateOf("")
        private set

    // Funções para atualizar os estados
    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    // Lógica de Login
    fun onLoginClick(onSuccess: () -> Unit) {
        // Validação simples: campos não podem estar vazios
        if (email.isNotBlank() && password.isNotBlank()) {
            // Futuramente aqui chamaremos a API via Retrofit
            onSuccess()
        }
    }
}
