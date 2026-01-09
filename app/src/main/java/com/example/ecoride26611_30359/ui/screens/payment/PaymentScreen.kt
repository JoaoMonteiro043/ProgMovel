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
    viewModel: PaymentViewModel = viewModel() // Injeção do ViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Formas de Pagamento",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Escolha a sua forma de pagamento:",
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Lista de métodos vinda do ViewModel
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

        // Spacer flexível para empurrar os botões para o fundo
        Spacer(modifier = Modifier.weight(1f))

        // Botões de Ação
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    val route = viewModel.getBackRoute(from)
                    navController.navigate(route)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(end = 6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Voltar", color = Color.White)
            }

            Button(
                onClick = {
                    viewModel.confirmPayment {
                        navController.navigate(AppRoutes.Home.route)
                    }
                },
                enabled = viewModel.selectedPayment != null,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(start = 6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Concluir", color = Color.White)
            }
        }
    }
}
