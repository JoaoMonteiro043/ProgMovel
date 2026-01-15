package com.example.ecoride26611_30359.ui.screens.driver

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.*

data class DashboardDriverUiState(
    val origem: String = "",
    val destino: String = "",
    val dataHora: String = "",
    val numLugares: String = "1",
    val errorMessage: String? = null
)

class DashboardDriverViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(DashboardDriverUiState())
    val uiState = _uiState.asStateFlow()

    fun updateOrigem(v: String) = _uiState.update { it.copy(origem = v, errorMessage = null) }
    fun updateDestino(v: String) = _uiState.update { it.copy(destino = v, errorMessage = null) }
    fun updateDataHora(v: String) = _uiState.update { it.copy(dataHora = v, errorMessage = null) }
    fun updateNumLugares(v: String) = _uiState.update { it.copy(numLugares = v, errorMessage = null) }

    // FUNÇÃO CORRIGIDA: Apenas valida, não insere na BD
    fun validarDados(onSuccess: (String, String, String, Int) -> Unit) {
        val state = _uiState.value

        if (state.origem.isBlank() || state.destino.isBlank() || state.dataHora.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Por favor, preencha todos os campos obrigatórios (*)") }
            return
        }

        val lotacao = state.numLugares.toIntOrNull() ?: 1
        onSuccess(state.origem, state.destino, state.dataHora, lotacao)
    }
}