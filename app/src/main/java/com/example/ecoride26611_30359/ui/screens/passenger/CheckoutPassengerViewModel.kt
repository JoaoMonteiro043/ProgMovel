package com.example.ecoride26611_30359.ui.screens.passenger

import androidx.lifecycle.ViewModel
import com.example.ecoride26611_30359.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Estado que contém os detalhes da viagem para checkout
data class CheckoutPassengerUiState(
    val tempoEstimado: String = "15 mins",
    val distancia: String = "25 km",
    val horaChegada: String = "21:47",
    val nomeCondutor: String = "João Silva",
    val fotoCondutor: Int = R.drawable.boy,
    val horaPartida: String = "21:26",
    val dataViagem: String = "25/11/2030",
    val pontoEncontro: String = "Parque da Cidade",
    val mapaImagem: Int = R.drawable.map
)

class CheckoutPassengerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CheckoutPassengerUiState())
    val uiState: StateFlow<CheckoutPassengerUiState> = _uiState.asStateFlow()

    // Aqui poderias ter lógica para carregar os dados da viagem selecionada
    init {
        loadTripDetails()
    }

    private fun loadTripDetails() {
        // Simulação: Numa app real, receberias o ID da viagem e carregarias os dados aqui
    }

    fun declineTrip(onNavigateBack: () -> Unit) {
        // Lógica para cancelar ou recusar
        onNavigateBack()
    }

    fun acceptTrip(onNavigateToPayment: () -> Unit) {
        // Lógica para confirmar reserva
        onNavigateToPayment()
    }
}

