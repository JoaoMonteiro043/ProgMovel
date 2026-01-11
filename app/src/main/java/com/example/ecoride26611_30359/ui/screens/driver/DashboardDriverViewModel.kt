package com.example.ecoride26611_30359.ui.screens.driver

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Estado que contém todos os dados do formulário e controlo de erros.
 */
data class DashboardDriverUiState(
    val origem: String = "",
    val destino: String = "",
    val dataHora: String = "",
    val numLugares: String = "",
    val informacoesCarro: String = "",
    val uploadCarta: String = "",
    val preferencias: String = "",
    val errorMessage: String? = null // Para mostrar mensagens de erro
)

class DashboardDriverViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardDriverUiState())
    val uiState: StateFlow<DashboardDriverUiState> = _uiState.asStateFlow()

    // Funções de atualização
    fun updateOrigem(newValue: String) {
        _uiState.update { it.copy(origem = newValue, errorMessage = null) }
    }

    fun updateDestino(newValue: String) {
        _uiState.update { it.copy(destino = newValue, errorMessage = null) }
    }

    fun updateDataHora(newValue: String) {
        _uiState.update { it.copy(dataHora = newValue, errorMessage = null) }
    }

    fun updateNumLugares(newValue: String) {
        _uiState.update { it.copy(numLugares = newValue, errorMessage = null) }
    }

    fun updateInformacoesCarro(newValue: String) {
        _uiState.update { it.copy(informacoesCarro = newValue, errorMessage = null) }
    }

    fun updateUploadCarta(newValue: String) {
        _uiState.update { it.copy(uploadCarta = newValue, errorMessage = null) }
    }

    fun updatePreferencias(newValue: String) {
        _uiState.update { it.copy(preferencias = newValue, errorMessage = null) }
    }

     /* Valida se todos os campos obrigatórios estão preenchidos */

    fun criarViagem(onSuccess: () -> Unit) {
        val state = _uiState.value

        if (state.origem.isBlank() ||
            state.destino.isBlank() ||
            state.dataHora.isBlank() ||
            state.numLugares.isBlank() ||
            state.informacoesCarro.isBlank() ||
            state.uploadCarta.isBlank()) {

            _uiState.update { it.copy(errorMessage = "Por favor, preencha todos os campos obrigatórios.") }
        } else {
            _uiState.update { it.copy(errorMessage = null) }
            onSuccess()
        }
    }
}
