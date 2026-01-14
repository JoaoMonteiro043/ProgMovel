package com.example.ecoride26611_30359.ui.screens.passenger

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
fun CheckoutPassengerScreen(
    navController: NavHostController,
    loggedUserId: Int, // Certifique-se que o AppNavigation passa este ID
    viewModel: CheckoutPassengerViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Dispara o carregamento ao abrir o ecrã
    LaunchedEffect(Unit) {
        viewModel.carregarDados(loggedUserId)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Resumo da Viagem", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Condutor: ${uiState.nomeCondutor}",
                    color = Color(0xFF1E88E5),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))
                Text("De: ${uiState.origem}", fontWeight = FontWeight.SemiBold)
                Text("Para: ${uiState.destino}", fontWeight = FontWeight.SemiBold)
                Text("Horário: ${uiState.dataViagem}", color = Color.Gray)
            }
        }

        if (uiState.jaReservou) {
            Text("Você já aceitou esta viagem!", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
        }

        uiState.errorMessage?.let {
            Text(it, color = Color.Red)
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.weight(1f).height(50.dp)
            ) {
                Text("Voltar")
            }

            Button(
                onClick = {
                    viewModel.acceptTrip(loggedUserId) {
                        navController.navigate(AppRoutes.Payment.route + "?from=passenger")
                    }
                },
                // DESATIVA O BOTÃO SE JÁ RESERVOU
                enabled = !uiState.jaReservou,
                modifier = Modifier.weight(1f).height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (uiState.jaReservou) Color.Gray else Color.Black
                )
            ) {
                Text(if (uiState.jaReservou) "Aceite" else "Aceitar", color = Color.White)
            }
        }
    }
}