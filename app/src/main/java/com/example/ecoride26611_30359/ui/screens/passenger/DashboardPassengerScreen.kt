package com.example.ecoride26611_30359.ui.screens.passenger

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ecoride26611_30359.navigation.AppRoutes
import java.net.URLDecoder

@Composable
fun DashboardPassengerScreen(
    navController: NavHostController,
    loggedUserId: Int,
    viewModel: DashboardPassengerViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Procurar Viagem", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(10.dp))

        // Inputs (não precisam decode)
        OutlinedTextField(
            value = uiState.origem,
            onValueChange = { viewModel.updateOrigem(it) },
            label = { Text("Origem") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = uiState.destino,
            onValueChange = { viewModel.updateDestino(it) },
            label = { Text("Destino") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.procurarViagens(loggedUserId) },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Pesquisar", color = Color.White)
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(uiState.viagensEncontradas) { viagem ->

                // 🔽 decode aqui
                val origemBonita = remember(viagem.origemLabel) {
                    URLDecoder.decode(viagem.origemLabel, "UTF-8")
                }
                val destinoBonito = remember(viagem.destinoLabel) {
                    URLDecoder.decode(viagem.destinoLabel, "UTF-8")
                }
                val dataBonita = remember(viagem.dataHora) {
                    URLDecoder.decode(viagem.dataHora, "UTF-8")
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("${AppRoutes.CheckoutPassenger.route}/${viagem.id}")
                        }
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(viagem.driverName, fontWeight = FontWeight.Bold, color = Color.Blue)
                            Text(
                                "Lugares: ${viagem.lugaresDisponiveis}",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4CAF50)
                            )
                        }
                        Text("De: $origemBonita -> Para: $destinoBonito")
                        Text("Data: $dataBonita", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}
