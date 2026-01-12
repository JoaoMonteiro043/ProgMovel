package com.example.ecoride26611_30359.ui.screens.passenger

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoride26611_30359.data.local.AppDatabase
import com.example.ecoride26611_30359.data.local.TripWithDriver
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class DashboardPassengerUiState(
    val origem: String = "",
    val destino: String = "",
    val viagensEncontradas: List<TripWithDriver> = emptyList()
)

class DashboardPassengerViewModel(application: Application) : AndroidViewModel(application) {
    private val tripDao = AppDatabase.getDatabase(application).tripDao()
    private val _uiState = MutableStateFlow(DashboardPassengerUiState())
    val uiState: StateFlow<DashboardPassengerUiState> = _uiState.asStateFlow()

    fun updateOrigem(v: String) = _uiState.update { it.copy(origem = v) }
    fun updateDestino(v: String) = _uiState.update { it.copy(destino = v) }

    fun procurarViagens() {
        val state = _uiState.value
        viewModelScope.launch {
            // Usamos a nova função com JOIN
            tripDao.getAllTripsWithDrivers().collect { todas ->
                val filtradas = todas.filter {
                    it.origem.contains(state.origem, ignoreCase = true) &&
                            it.destino.contains(state.destino, ignoreCase = true)
                }
                _uiState.update { it.copy(viagensEncontradas = filtradas) }
            }
        }
    }
}
