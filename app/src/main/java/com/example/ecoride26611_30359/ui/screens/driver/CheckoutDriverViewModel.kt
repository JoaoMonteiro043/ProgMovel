package com.example.ecoride26611_30359.ui.screens.driver

import androidx.lifecycle.ViewModel
import com.example.ecoride26611_30359.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Estado que contém os detalhes da viagem para o condutor validar
data class CheckoutDriverUiState(
    val tempoEstimado: String = "15 mins",
    val distancia: String = "25 km",
    val horaChegada: String = "21:47",
    val nomePassageiro: String = "Maria Oliveira",
    val fotoPassageiro: Int = R.drawable.girl,
    val horaPartida: String = "21:26",
    val dataViagem: String = "25/11/2030",
    val pontoEncontro: String = "Parque da Cidade",
    val mapaImagem: Int = R.drawable.map
)

class CheckoutDriverViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CheckoutDriverUiState())
    val uiState: StateFlow<CheckoutDriverUiState> = _uiState.asStateFlow()

    init {
        // Aqui carregarias os dados do passageiro que solicitou a viagem
        loadRequestDetails()
    }

    private fun loadRequestDetails() {
        // Simulação de carregamento
    }

    fun acceptRequest(onSuccess: () -> Unit) {
        // Lógica para confirmar no servidor que o condutor aceitou
        onSuccess()
    }

    fun declineRequest(onCancel: () -> Unit) {
        // Lógica para rejeitar o pedido
        onCancel()
    }
}
