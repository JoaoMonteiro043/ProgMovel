package com.example.ecoride26611_30359.ui.screens.passenger

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
fun DashboardPassengerScreen(
    navController: NavHostController,
    viewModel: DashboardPassengerViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Bar
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.navigate(AppRoutes.Home.route) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar"
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Procurar Viagem",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Mensagem de Erro
        uiState.errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp),
                fontWeight = FontWeight.SemiBold
            )
        }

        // Campo ORIGEM
        OutlinedTextField(
            value = uiState.origem,
            onValueChange = { viewModel.updateOrigem(it) },
            label = { Text("Origem *") },
            placeholder = { Text("Ex: Lisboa") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null && uiState.origem.isBlank()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo DESTINO
        OutlinedTextField(
            value = uiState.destino,
            onValueChange = { viewModel.updateDestino(it) },
            label = { Text("Destino *") },
            placeholder = { Text("Ex: Porto") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null && uiState.destino.isBlank()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo DATA E HORA
        OutlinedTextField(
            value = uiState.dataHora,
            onValueChange = { viewModel.updateDataHora(it) },
            label = { Text("Data e Hora") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Filtros Opcionais
        Text("Filtros Opcionais", fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = uiState.filtros,
            onValueChange = { viewModel.updateFiltros(it) },
            placeholder = { Text("Ex: Ar condicionado, sem fumadores") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Preferências
        Text("Preferências", fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = uiState.preferencias,
            onValueChange = { viewModel.updatePreferencias(it) },
            placeholder = { Text("Ex: Gosto de conversar, música calma") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 2
        )

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(32.dp))

        // Botão Procurar
        Button(
            onClick = {
                viewModel.procurarViagem {
                    navController.navigate(AppRoutes.CheckoutPassenger.route)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Procurar!", color = Color.White, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
