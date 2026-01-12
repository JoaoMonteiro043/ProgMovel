package com.example.ecoride26611_30359.ui.screens.payment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PaymentViewModel : ViewModel() {

    val paymentMethods = listOf(
        "Cartão de Crédito",
        "PayPal",
        "MbWay",
        "Google Pay"
    )

    // Estado para armazenar o método selecionado
    var selectedPayment by mutableStateOf<String?>(null)
        private set

    fun onPaymentMethodSelected(method: String) {
        selectedPayment = method
    }

    // A lógica de voltar é tratada pelo popBackStack() no PaymentScreen.kt
    // para evitar crashes por falta de argumentos na rota de Checkout.

    fun confirmPayment(onSuccess: () -> Unit) {
        if (selectedPayment != null) {
            // Aqui seria processado o pagamento
            onSuccess()
        }
    }
}
