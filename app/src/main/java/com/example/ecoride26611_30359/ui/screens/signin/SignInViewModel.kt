/*package com.example.ecoride26611_30359.ui.screens.signin

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
} */

package com.example.ecoride26611_30359.ui.screens.signin

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoride26611_30359.data.local.AppDatabase
import com.example.ecoride26611_30359.data.local.UserEntity
import kotlinx.coroutines.launch

class SignInViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)

    fun onNameChange(v: String) { name = v }
    fun onEmailChange(v: String) { email = v }
    fun onPasswordChange(v: String) { password = v }

    fun performSignIn(onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (name.isBlank() || email.isBlank() || password.length < 6) {
                errorMessage = "Dados inválidos (Password min. 6 chars)"
                return@launch
            }

            val existingUser = userDao.getUserByEmail(email)
            if (existingUser != null) {
                errorMessage = "Este email já está registado"
            } else {
                userDao.registerUser(UserEntity(name = name, email = email, password = password))
                onSuccess()
            }
        }
    }
}

