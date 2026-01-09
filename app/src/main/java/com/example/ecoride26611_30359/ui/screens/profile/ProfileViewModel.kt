package com.example.ecoride26611_30359.ui.screens.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {

    // Movi a data class para aqui dentro para o ficheiro ser lido como uma única Classe
    data class ProfileUiState(
        val userName: String = "Alfredo Martins",
        val userEmail: String = "alfredo.martins@email.com",
        val userRating: Double = 4.7,
        val profileImageRes: Int? = null
    )

    // Estado privado (mutável) e público (apenas leitura)
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    /**
     * Lógica para realizar logout.
     */
    fun onLogout(onSuccess: () -> Unit) {
        // No futuro: Firebase.auth.signOut()
        onSuccess()
    }
}
