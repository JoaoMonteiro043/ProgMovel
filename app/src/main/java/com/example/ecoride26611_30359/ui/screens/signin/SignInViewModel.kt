package com.example.ecoride26611_30359.ui.screens.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {

    // Estados dos campos
    var name by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    // Funções para atualizar os estados
    fun onNameChange(newName: String) {
        name = newName
    }

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    /**
     * Lógica de validação e registo.
     * Retorna true se os dados forem válidos para avançar.
     */
    fun performSignIn(onSuccess: () -> Unit) {
        if (name.isNotBlank() && email.contains("@") && password.length >= 6) {
            // Aqui futuramente chamará o seu Repository/Retrofit
            onSuccess()
        } else {
            // Aqui poderia gerir mensagens de erro para o utilizador
        }
    }
}
