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
import androidx.navigation.NavHostController
import com.example.ecoride26611_30359.navigation.AppRoutes

@Composable
fun PaymentScreen(navController: NavHostController, from: String?) {

    val paymentMethods = listOf(
        "Cartão de Crédito",
        "PayPal",
        "MbWay",
        "Google Pay"
    )

    var selectedPayment by remember { mutableStateOf<String?>(null) }

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

        // Lista de métodos de pagamento
        paymentMethods.forEach { method ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp)
                    .height(50.dp)
                    .background(Color(0xFFE0E0E0), shape = MaterialTheme.shapes.small)
                    .clickable { selectedPayment = method }
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(method, fontSize = 16.sp)
                RadioButton(
                    selected = selectedPayment == method,
                    onClick = { selectedPayment = method }
                )
            }
        }

        Spacer(modifier = Modifier.height(240.dp))

        // Botões
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                onClick = {
                    if (from == "driver") {
                        navController.navigate(AppRoutes.CheckoutDriver.route)
                    } else {
                        navController.navigate(AppRoutes.CheckoutPassenger.route)
                    }
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
                    // 📌 Futuro: Confirmação de pagamento
                    navController.navigate(AppRoutes.Home.route)
                },
                enabled = selectedPayment != null,
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
