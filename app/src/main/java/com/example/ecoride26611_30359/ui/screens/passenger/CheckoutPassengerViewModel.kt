package com.example.ecoride26611_30359.ui.screens.passenger

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.ecoride26611_30359.data.local.AppDatabase
import com.example.ecoride26611_30359.data.local.TripWithDriver
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CheckoutPassengerUiState(
    val origem: String = "",
    val destino: String = "",
    val dataViagem: String = "",
    val nomeCondutor: String = ""
)

class CheckoutPassengerViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val tripDao = AppDatabase.getDatabase(application).tripDao()

    // Recupera o ID da viagem passado pela navegação
    private val tripId: Int = savedStateHandle.get<Int>("tripId") ?: -1

    private val _uiState = MutableStateFlow(CheckoutPassengerUiState())
    val uiState: StateFlow<CheckoutPassengerUiState> = _uiState.asStateFlow()

    init {
        if (tripId != -1) {
            carregarDetalhesViagem()
        }
    }

    private fun carregarDetalhesViagem() {
        viewModelScope.launch {
            // Agora o compilador já reconhece o TripWithDriver devido ao import acima
            val viagem: TripWithDriver? = tripDao.getTripWithDriverById(tripId)
            viagem?.let { v ->
                _uiState.update { it.copy(
                    origem = v.origem,
                    destino = v.destino,
                    dataViagem = v.dataHora,
                    nomeCondutor = v.driverName
                ) }
            }
        }
    }

    fun acceptTrip(onSuccess: () -> Unit) {
        onSuccess()
    }
}
