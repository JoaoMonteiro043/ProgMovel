package com.example.ecoride26611_30359.ui.screens.passenger

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.ecoride26611_30359.R
import com.example.ecoride26611_30359.data.local.AppDatabase
import com.example.ecoride26611_30359.data.local.ReservationEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CheckoutPassengerUiState(
    val origem: String = "",
    val destino: String = "",
    val dataViagem: String = "",
    val nomeCondutor: String = "",
    val carro: String = "",
    val matricula: String = "",
    val distancia: String = "42 km", // Simulado
    val tempoEstimado: String = "35 mins", // Simulado
    val mapaImagem: Int = R.drawable.map, // Recurso de imagem
    val jaReservou: Boolean = false,
    val isLoading: Boolean = true
)

class CheckoutPassengerViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val tripDao = AppDatabase.getDatabase(application).tripDao()
    private val tripId: Int = savedStateHandle.get<Int>("tripId") ?: -1

    private val _uiState = MutableStateFlow(CheckoutPassengerUiState())
    val uiState: StateFlow<CheckoutPassengerUiState> = _uiState.asStateFlow()

    fun carregarDados(userId: Int) {
        if (tripId == -1) return
        viewModelScope.launch {
            val count = tripDao.hasUserReservedTrip(userId, tripId)
            tripDao.getTripWithDriverById(tripId)?.let { v ->
                _uiState.update { it.copy(
                    origem = v.origemLabel,
                    destino = v.destinoLabel,
                    dataViagem = v.dataHora,
                    nomeCondutor = v.driverName,
                    carro = v.carro,
                    matricula = v.matricula,
                    jaReservou = count > 0,
                    isLoading = false
                ) }
            }
        }
    }

    fun acceptTrip(userId: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (tripId == -1) return@launch
            tripDao.reserveSeat(tripId)
            tripDao.insertReservation(ReservationEntity(userId = userId, tripId = tripId))
            onSuccess()
        }
    }
}