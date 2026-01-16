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
import com.example.ecoride26611_30359.utils.GeoUtils

data class CheckoutPassengerUiState(
    val origem: String = "",
    val destino: String = "",
    val dataViagem: String = "",
    val nomeCondutor: String = "",
    val carro: String = "",
    val matricula: String = "",
    val distanciaKm: Double? = null,
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

    private val checkpointDao =
        AppDatabase.getDatabase(application).checkpointDao()

    private val _uiState = MutableStateFlow(CheckoutPassengerUiState())
    val uiState: StateFlow<CheckoutPassengerUiState> = _uiState.asStateFlow()

    fun carregarDados(userId: Int) {
        if (tripId == -1) return

        viewModelScope.launch {
            val count = tripDao.hasUserReservedTrip(userId, tripId)

            val trip = tripDao.getTripById(tripId)
            val tripUI = tripDao.getTripWithDriverById(tripId)

            if (trip != null && tripUI != null) {

                val origem = checkpointDao.getById(trip.origemCheckpointId)
                val destino = checkpointDao.getById(trip.destinoCheckpointId)

                val distancia = if (origem != null && destino != null) {

                    val linhaReta = GeoUtils.distanceKm(
                        origem.lat,
                        origem.lng,
                        destino.lat,
                        destino.lng
                    )

                    linhaReta * 1.15   // ajuste para distância real de estrada

                } else null

                _uiState.update {
                    it.copy(
                        origem = tripUI.origemLabel,
                        destino = tripUI.destinoLabel,
                        dataViagem = tripUI.dataHora,
                        nomeCondutor = tripUI.driverName,
                        carro = tripUI.carro,
                        matricula = tripUI.matricula,
                        distanciaKm = distancia,
                        jaReservou = count > 0,
                        isLoading = false
                    )
                }
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