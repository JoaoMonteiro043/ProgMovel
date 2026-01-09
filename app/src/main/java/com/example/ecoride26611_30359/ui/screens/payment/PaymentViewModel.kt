package com.example.ecoride26611_30359.ui.screens.payment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ecoride26611_30359.navigation.AppRoutes

class PaymentViewModel : ViewModel() {

    // Lista de métodos de pagamento (poderia vir de uma API)
    val paymentMethods = listOf(
        "Cartão de Crédito",
        "PayPal",
        "MbWay",
        "Google Pay"
    )

    // Estado para o método selecionado
    var selectedPayment by mutableStateOf<String?>(null)
        private set

    // Função para atualizar a seleção do utilizador
    fun onPaymentMethodSelected(method: String) {
        selectedPayment = method
    }

    /**
     * Lógica para o botão "Voltar" baseada na origem (from)
     */
    fun getBackRoute(from: String?): String {
        return if (from == "driver") {
            AppRoutes.CheckoutDriver.route
        } else {
            AppRoutes.CheckoutPassenger.route
        }
    }

    /**
     * Simulação de lógica de confirmação de pagamento
     */
    fun confirmPayment(onSuccess: () -> Unit) {
        if (selectedPayment != null) {
            // Aqui entraria a integração com Stripe/PayPal/etc
            println("Pagamento processado com: $selectedPayment")
            onSuccess()
        }
    }
}
