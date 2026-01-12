package com.example.ecoride26611_30359.ui.screens.passenger

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ecoride26611_30359.navigation.AppRoutes

@Composable
fun DashboardPassengerScreen(
    navController: NavHostController,
    viewModel: DashboardPassengerViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Procurar Viagem", fontSize = 22.sp, fontWeight = FontWeight.Bold)

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
            onClick = { viewModel.procurarViagens() },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Pesquisar")
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(uiState.viagensEncontradas) { viagem ->
                Card(modifier = Modifier.fillMaxWidth().clickable {
                    // Navegação usando a rota definida no AppNavigation
                    navController.navigate("${AppRoutes.CheckoutPassenger.route}/${viagem.id}")
                }) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("De: ${viagem.origem} -> Para: ${viagem.destino}", fontWeight = FontWeight.Bold)
                        Text("Data: ${viagem.dataHora}", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
