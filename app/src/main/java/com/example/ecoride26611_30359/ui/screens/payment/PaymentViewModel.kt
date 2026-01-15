package com.example.ecoride26611_30359.ui.screens.payment

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoride26611_30359.data.local.*
import kotlinx.coroutines.launch

class PaymentViewModel(application: Application) : AndroidViewModel(application) {

    private val tripDao = AppDatabase.getDatabase(application).tripDao()

    val paymentMethods = listOf("Cartão de Crédito", "PayPal", "MbWay", "Google Pay")

    var selectedPayment by mutableStateOf<String?>(null)
        private set

    fun onPaymentMethodSelected(method: String) {
        selectedPayment = method
    }

    fun confirmPayment(
        userId: Int,
        tripId: Int,
        from: String?,
        origem: String? = null,
        destino: String? = null,
        data: String? = null,
        lugares: Int = 1,
        onSuccess: () -> Unit
    ) {
        if (selectedPayment != null && userId != -1) {
            viewModelScope.launch {
                if (from == "passenger" && tripId != -1) {
                    // FLUXO PASSAGEIRO: Reserva lugar
                    val jaReservou = tripDao.hasUserReservedTrip(userId, tripId) > 0
                    if (!jaReservou) {
                        tripDao.reserveSeat(tripId)
                        tripDao.insertReservation(ReservationEntity(userId = userId, tripId = tripId))
                    }
                } else if (from == "driver") {
                    // FLUXO CONDUTOR: CRIAR VIAGEM E CHAT APENAS AGORA
                    if (origem != null && destino != null && data != null) {
                        val newTripId = tripDao.insertTrip(TripEntity(
                            userId = userId,
                            origem = origem,
                            destino = destino,
                            dataHora = data,
                            lugaresTotal = lugares,
                            lugaresDisponiveis = lugares
                        ))

                        tripDao.insertChat(ChatEntity(
                            tripId = newTripId.toInt(),
                            groupName = "Viagem: $origem - $destino"
                        ))
                    }
                }
                onSuccess()
            }
        }
    }
}