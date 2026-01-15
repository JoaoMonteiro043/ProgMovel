package com.example.ecoride26611_30359.ui.screens.driver

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoride26611_30359.R
import com.example.ecoride26611_30359.data.local.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CheckoutDriverUiState(
    val origem: String = "",
    val destino: String = "",
    val numLugares: Int = 0, // Adicionado campo
    val tempoEstimado: String = "45 mins",
    val distancia: String = "75 km",
    val mapaImagem: Int = R.drawable.map
)

class CheckoutDriverViewModel(application: Application) : AndroidViewModel(application) {

    private val tripDao = AppDatabase.getDatabase(application).tripDao()

    private val _uiState = MutableStateFlow(CheckoutDriverUiState())
    val uiState: StateFlow<CheckoutDriverUiState> = _uiState.asStateFlow()

    init {
        carregarUltimaViagem()
    }

    private fun carregarUltimaViagem() {
        viewModelScope.launch {
            tripDao.getLastTrip().collect { trip ->
                trip?.let { v ->
                    _uiState.update { it.copy(
                        origem = v.origemLabel,
                        destino = v.destinoLabel,
                        numLugares = v.lugaresTotal
                    ) }
                }
            }
        }
    }

    fun confirmarViagem(onSuccess: () -> Unit) {
        onSuccess()
    }
}
