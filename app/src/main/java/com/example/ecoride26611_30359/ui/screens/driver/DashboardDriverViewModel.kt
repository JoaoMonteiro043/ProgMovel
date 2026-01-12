package com.example.ecoride26611_30359.ui.screens.driver

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoride26611_30359.data.local.AppDatabase
import com.example.ecoride26611_30359.data.local.TripEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DashboardDriverUiState(
    val origem: String = "",
    val destino: String = "",
    val dataHora: String = "",
    val numLugares: String = "",
    val errorMessage: String? = null
)

class DashboardDriverViewModel(application: Application) : AndroidViewModel(application) {

    private val tripDao = AppDatabase.getDatabase(application).tripDao()

    private val _uiState = MutableStateFlow(DashboardDriverUiState())
    val uiState: StateFlow<DashboardDriverUiState> = _uiState.asStateFlow()

    fun updateOrigem(newValue: String) {
        _uiState.update { it.copy(origem = newValue, errorMessage = null) }
    }

    fun updateDestino(newValue: String) {
        _uiState.update { it.copy(destino = newValue, errorMessage = null) }
    }

    fun updateDataHora(newValue: String) {
        _uiState.update { it.copy(dataHora = newValue) }
    }

    fun updateNumLugares(newValue: String) {
        _uiState.update { it.copy(numLugares = newValue) }
    }

    fun criarViagem(userId: Int, onSuccess: () -> Unit) {
        val state = _uiState.value

        if (state.origem.isBlank() || state.destino.isBlank() || state.dataHora.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Campos obrigatórios em falta.") }
        } else if (userId == -1) {
            _uiState.update { it.copy(errorMessage = "Erro de sessão. Faça login novamente.") }
        } else {
            viewModelScope.launch {
                try {
                    val novaTrip = TripEntity(
                        userId = userId, // GRAVA O ID DO USER QUE ESTÁ LOGADO
                        origem = state.origem,
                        destino = state.destino,
                        dataHora = state.dataHora,
                        latOrigem = 0.0,
                        lngOrigem = 0.0,
                        latDestino = 0.0,
                        lngDestino = 0.0
                    )
                    tripDao.insertTrip(novaTrip)
                    onSuccess()
                } catch (e: Exception) {
                    _uiState.update { it.copy(errorMessage = "Erro ao guardar: ${e.message}") }
                }
            }
        }
    }
}
