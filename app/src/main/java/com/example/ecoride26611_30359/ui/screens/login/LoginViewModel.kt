package com.example.ecoride26611_30359.ui.screens.login

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoride26611_30359.data.local.AppDatabase
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)

    fun onEmailChange(v: String) { email = v }
    fun onPasswordChange(v: String) { password = v }

    // Mudança aqui: onSuccess agora recebe um Int (o ID do user)
    fun onLoginClick(onSuccess: (Int) -> Unit) {
        viewModelScope.launch {
            val user = userDao.login(email, password)
            if (user != null) {
                onSuccess(user.id) // Enviamos o ID real do João ou Diogo
            } else {
                errorMessage = "Email ou password incorretos"
            }
        }
    }
}
