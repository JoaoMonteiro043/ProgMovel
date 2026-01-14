package com.example.ecoride26611_30359.ui.screens.passenger

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.ecoride26611_30359.data.local.AppDatabase
import com.example.ecoride26611_30359.data.local.ReservationEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CheckoutPassengerUiState(
    val origem: String = "",
    val destino: String = "",
    val dataViagem: String = "",
    val nomeCondutor: String = "",
    val jaReservou: Boolean = false,
    val errorMessage: String? = null
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

            // Agora a função existe no DAO
            tripDao.getTripWithDriverById(tripId)?.let { v ->
                _uiState.update { it.copy(
                    origem = v.origem,
                    destino = v.destino,
                    dataViagem = v.dataHora,
                    nomeCondutor = v.driverName,
                    jaReservou = count > 0
                ) }
            }
        }
    }

    fun acceptTrip(userId: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val check = tripDao.hasUserReservedTrip(userId, tripId)
            if (check > 0) {
                _uiState.update { it.copy(errorMessage = "Já reservou esta viagem!") }
                return@launch
            }

            tripDao.reserveSeat(tripId)
            tripDao.insertReservation(ReservationEntity(userId = userId, tripId = tripId))
            onSuccess()
        }
    }
}