package com.example.ecoride26611_30359.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    // No futuro, este nome viria da base de dados após o login
    var userName by mutableStateOf("Utilizador")
        private set

    /**
     * Lógica para carregar dados iniciais da Home, se necessário.
     */
    init {
        // Exemplo: Simular carregamento do nome do utilizador
        userName = "Nuno"
    }

    // Funções de clique podem ser centralizadas aqui se houver lógica extra (ex: Analytics)
    fun onNavigateToDriver(onNavigate: () -> Unit) {
        onNavigate()
    }

    fun onNavigateToPassenger(onNavigate: () -> Unit) {
        onNavigate()
    }
}
