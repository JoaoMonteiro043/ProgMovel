package com.example.ecoride26611_30359.ui.screens.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoride26611_30359.data.local.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val userDao = database.userDao()
    private val tripDao = database.tripDao()

    data class ProfileUiState(
        val userName: String = "",
        val userEmail: String = "",
        val carta: String = "",
        val carro: String = "",
        val matricula: String = "",
        val hasTrips: Boolean = false,
        val isLoading: Boolean = true,
        val errorMessage: String? = null
    )

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun loadUserProfile(userId: Int) {
        if (userId == -1) return
        viewModelScope.launch {
            // 1. Obter dados do utilizador
            val user = userDao.getUserById(userId)

            // 2. Verificar se o utilizador tem viagens criadas
            // Obtemos a lista de chats (que representam as viagens) e verificamos se não está vazia
            val userChats = tripDao.getChatsForUser(userId).first()
            val hasTripsCreated = userChats.isNotEmpty()

            user?.let { u ->
                _uiState.update { it.copy(
                    userName = u.name,
                    userEmail = u.email,
                    carta = u.cartaConducao ?: "",
                    carro = u.carro ?: "",
                    matricula = u.matricula ?: "",
                    hasTrips = hasTripsCreated,
                    isLoading = false
                ) }
            }
        }
    }

    fun updateProfile(userId: Int, carta: String, carro: String, matricula: String) {
        if (userId == -1) return

        // Bloqueio de segurança: Se tem viagens, não permite avançar
        if (_uiState.value.hasTrips) {
            _uiState.update { it.copy(errorMessage = "Não pode editar o veículo ou carta enquanto tiver viagens ativas.") }
            return
        }

        viewModelScope.launch {
            try {
                val currentUser = userDao.getUserById(userId)
                if (currentUser != null) {
                    val updatedUser = currentUser.copy(
                        cartaConducao = carta,
                        carro = carro,
                        matricula = matricula
                    )
                    userDao.registerUser(updatedUser)
                    _uiState.update { it.copy(errorMessage = "Perfil atualizado com sucesso!") }
                    loadUserProfile(userId) // Recarrega para atualizar a UI
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Erro ao guardar: ${e.message}") }
            }
        }
    }

    fun onLogout(onSuccess: () -> Unit) = onSuccess()
}