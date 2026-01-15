package com.example.ecoride26611_30359.ui.screens.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoride26611_30359.data.local.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getDatabase(application).userDao()

    data class ProfileUiState(
        val userName: String = "",
        val userEmail: String = "",
        val carta: String = "",
        val carro: String = "",
        val matricula: String = "",
        val isLoading: Boolean = true,
        val errorMessage: String? = null
    )

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun loadUserProfile(userId: Int) {
        if (userId == -1) return
        viewModelScope.launch {
            val user = userDao.getUserById(userId)
            user?.let { u ->
                _uiState.update { it.copy(
                    userName = u.name,
                    userEmail = u.email,
                    carta = u.cartaConducao,
                    carro = u.carro,
                    matricula = u.matricula,
                    isLoading = false
                ) }
            }
        }
    }

    fun updateProfile(userId: Int, carta: String, carro: String, matricula: String) {
        if (userId == -1) return
        viewModelScope.launch {
            try {
                val currentUser = userDao.getUserById(userId)
                if (currentUser != null) {
                    // Criamos uma cópia mantendo o ID, Nome, Email e Password originais
                    val updatedUser = currentUser.copy(
                        cartaConducao = carta,
                        carro = carro,
                        matricula = matricula
                    )
                    userDao.registerUser(updatedUser) // Irá fazer REPLACE/Update
                    loadUserProfile(userId) // Recarregar para confirmar
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Erro ao guardar: ${e.message}") }
            }
        }
    }

    fun onLogout(onSuccess: () -> Unit) = onSuccess()
}