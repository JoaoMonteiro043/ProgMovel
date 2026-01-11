package com.example.ecoride26611_30359.ui.screens.passenger

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Estado que contém os dados de pesquisa do passageiro.
 */
data class DashboardPassengerUiState(
    val origem: String = "",
    val destino: String = "",
    val dataHora: String = "25/11/2030 12:00",
    val filtros: String = "",
    val preferencias: String = "",
    val errorMessage: String? = null
)

class DashboardPassengerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardPassengerUiState())
    val uiState: StateFlow<DashboardPassengerUiState> = _uiState.asStateFlow()

    fun updateOrigem(newValue: String) {
        _uiState.update { it.copy(origem = newValue, errorMessage = null) }
    }

    fun updateDestino(newValue: String) {
        _uiState.update { it.copy(destino = newValue, errorMessage = null) }
    }

    fun updateDataHora(newValue: String) {
        _uiState.update { it.copy(dataHora = newValue, errorMessage = null) }
    }

    fun updateFiltros(newValue: String) {
        _uiState.update { it.copy(filtros = newValue) }
    }

    fun updatePreferencias(newValue: String) {
        _uiState.update { it.copy(preferencias = newValue) }
    }

    fun procurarViagem(onSuccess: () -> Unit) {
        val state = _uiState.value
        // Validação: Origem e Destino são obrigatórios
        if (state.origem.isBlank() || state.destino.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Por favor, indique a origem e o destino.") }
        } else {
            _uiState.update { it.copy(errorMessage = null) }
            onSuccess()
        }
    }
}


