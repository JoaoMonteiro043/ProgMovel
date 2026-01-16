package com.example.ecoride26611_30359.ui.screens.driver

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ecoride26611_30359.data.local.AppDatabase
import kotlinx.coroutines.flow.*

data class DashboardDriverUiState(
    val origem: String = "",
    val destino: String = "",
    val dataHora: String = "",
    val numLugares: String = "1",
    val errorMessage: String? = null
)

class DashboardDriverViewModel(application: Application) : AndroidViewModel(application) {

    private val tripDao = AppDatabase.getDatabase(application).tripDao()

    data class UiState(
        val origemCheckpointId: Int? = null,
        val destinoCheckpointId: Int? = null,
        val origemLabel: String = "",
        val destinoLabel: String = "",
        val dataHora: String = "",
        val horaPartida: String = "",
        val numLugares: String = "",
        val errorMessage: String? = null
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun updateOrigem(id: Int, label: String) {
        _uiState.update { it.copy(origemCheckpointId = id, origemLabel = label) }
    }

    fun updateDestino(id: Int, label: String) {
        _uiState.update { it.copy(destinoCheckpointId = id, destinoLabel = label) }
    }

    fun updateDataHora(v: String) = _uiState.update { it.copy(dataHora = v) }
    fun updateNumLugares(v: String) = _uiState.update { it.copy(numLugares = v) }

    fun validarDados(onSuccess: (Int, Int, String, Int) -> Unit) {
        val s = _uiState.value
        if (s.origemCheckpointId == null || s.destinoCheckpointId == null || s.dataHora.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Preencha todos os campos") }
            return
        }
        onSuccess(s.origemCheckpointId, s.destinoCheckpointId, s.dataHora, s.numLugares.toIntOrNull() ?: 1)
    }
}
