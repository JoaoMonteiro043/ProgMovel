package com.example.ecoride26611_30359.ui.screens.driver

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoride26611_30359.data.local.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class DashboardDriverUiState(
    val origem: String = "",
    val destino: String = "",
    val dataHora: String = "",
    val numLugares: String = "1",
    val errorMessage: String? = null
)

class DashboardDriverViewModel(application: Application) : AndroidViewModel(application) {
    private val tripDao = AppDatabase.getDatabase(application).tripDao()
    private val _uiState = MutableStateFlow(DashboardDriverUiState())
    val uiState = _uiState.asStateFlow()

    fun updateOrigem(v: String) = _uiState.update { it.copy(origem = v) }
    fun updateDestino(v: String) = _uiState.update { it.copy(destino = v) }
    fun updateDataHora(v: String) = _uiState.update { it.copy(dataHora = v) }
    fun updateNumLugares(v: String) = _uiState.update { it.copy(numLugares = v) }

    fun criarViagem(userId: Int, onSuccess: () -> Unit) {
        val state = _uiState.value
        val lotacao = state.numLugares.toIntOrNull() ?: 1

        viewModelScope.launch {
            // 1. Cria a Viagem
            val tripId = tripDao.insertTrip(TripEntity(
                userId = userId, origem = state.origem, destino = state.destino,
                dataHora = state.dataHora, lugaresTotal = lotacao, lugaresDisponiveis = lotacao
            ))

            // 2. Cria o Chat associado à viagem
            tripDao.insertChat(ChatEntity(
                tripId = tripId.toInt(),
                groupName = "Viagem: ${state.origem} - ${state.destino}"
            ))
            onSuccess()
        }
    }
}