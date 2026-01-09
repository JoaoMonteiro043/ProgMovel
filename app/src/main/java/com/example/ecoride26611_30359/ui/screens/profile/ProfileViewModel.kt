package com.example.ecoride26611_30359.ui.screens.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Classe de dados para representar o estado do perfil
data class ProfileUiState(
    val userName: String = "Alfredo Martins",
    val userEmail: String = "alfredo.martins@email.com",
    val userRating: Double = 4.7,
    val profileImageRes: Int? = null // Pode ser usado para trocar a imagem dinamicamente
)

class ProfileViewModel : ViewModel() {

    // Estado privado (mutável) e público (apenas leitura)
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    /**
     * Lógica para realizar logout.
     * No futuro, aqui poderás limpar tokens de autenticação ou base de dados local.
     */
    fun onLogout(onSuccess: () -> Unit) {
        // Lógica de logout (ex: Firebase.auth.signOut())
        onSuccess()
    }
}
