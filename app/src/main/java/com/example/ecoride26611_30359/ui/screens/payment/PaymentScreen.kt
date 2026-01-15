package com.example.ecoride26611_30359.ui.screens.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ecoride26611_30359.navigation.AppRoutes

@Composable
fun PaymentScreen(
    navController: NavHostController,
    from: String?,
    tripId: Int,
    userId: Int,

    origemCheckpointId: Int = -1,
    destinoCheckpointId: Int = -1,

    origemLabel: String? = null,
    destinoLabel: String? = null,
    data: String? = null,
    lugares: Int = 1,
    viewModel: PaymentViewModel = viewModel()
) {

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp).padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Formas de Pagamento", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(50.dp))
        Text("Escolha a sua forma de pagamento:", fontSize = 16.sp, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(20.dp))

        viewModel.paymentMethods.forEach { method ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(55.dp)
                    .background(
                        color = if (viewModel.selectedPayment == method) Color(0xFFEEEEEE) else Color(0xFFF5F5F5),
                        shape = MaterialTheme.shapes.small
                    )
                    .clickable { viewModel.onPaymentMethodSelected(method) }
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = method, fontSize = 16.sp)
                RadioButton(
                    selected = (viewModel.selectedPayment == method),
                    onClick = { viewModel.onPaymentMethodSelected(method) }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)) {

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.weight(1f).height(50.dp).padding(end = 6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Voltar", color = Color.White)
            }

            Button(
                onClick = {
                    viewModel.confirmPayment(
                        userId = userId,
                        tripId = tripId,
                        from = from,
                        origemCheckpointId = origemCheckpointId,
                        destinoCheckpointId = destinoCheckpointId,
                        origemLabel = origemLabel,
                        destinoLabel = destinoLabel,
                        data = data,
                        lugares = lugares
                    ) {
                        navController.navigate(AppRoutes.Home.route) {
                            popUpTo(AppRoutes.Home.route) { inclusive = true }
                        }
                    }
                },
                enabled = viewModel.selectedPayment != null,
                modifier = Modifier.weight(1f).height(50.dp).padding(start = 6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                val textoBotao = if (from == "driver") "Pagar e Publicar" else "Concluir e Reservar"
                Text(textoBotao, color = Color.White)
            }
        }
    }
}
