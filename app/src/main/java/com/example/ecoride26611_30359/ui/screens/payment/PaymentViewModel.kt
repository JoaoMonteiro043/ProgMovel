package com.example.ecoride26611_30359.ui.screens.payment

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoride26611_30359.data.local.AppDatabase
import com.example.ecoride26611_30359.data.local.ReservationEntity
import kotlinx.coroutines.launch

class PaymentViewModel(application: Application) : AndroidViewModel(application) {

    private val tripDao = AppDatabase.getDatabase(application).tripDao()

    val paymentMethods = listOf(
        "Cartão de Crédito",
        "PayPal",
        "MbWay",
        "Google Pay"
    )

    var selectedPayment by mutableStateOf<String?>(null)
        private set

    fun onPaymentMethodSelected(method: String) {
        selectedPayment = method
    }

    // A reserva só é efetivada aqui, após o "pagamento"
    fun confirmPayment(userId: Int, tripId: Int, onSuccess: () -> Unit) {
        if (selectedPayment != null && userId != -1 && tripId != -1) {
            viewModelScope.launch {
                // Verificar se já não reservou (prevenção contra duplo clique)
                val jaReservou = tripDao.hasUserReservedTrip(userId, tripId) > 0

                if (!jaReservou) {
                    // 1. Retirar lugar na viagem
                    tripDao.reserveSeat(tripId)
                    // 2. Criar a reserva (que dá acesso automático ao chat)
                    tripDao.insertReservation(ReservationEntity(userId = userId, tripId = tripId))
                }
                onSuccess()
            }
        }
    }
}