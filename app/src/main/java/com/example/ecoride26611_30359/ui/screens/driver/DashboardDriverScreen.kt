package com.example.ecoride26611_30359.ui.screens.driver

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
fun DashboardDriverScreen(
    navController: NavHostController,
    viewModel: DashboardDriverViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Cabeçalho
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.navigate(AppRoutes.Home.route) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar"
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Criar Viagem",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Mensagem de Erro (se existir)
        uiState.errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp),
                fontWeight = FontWeight.Bold
            )
        }

        // Campo Origem
        OutlinedTextField(
            value = uiState.origem,
            onValueChange = { viewModel.updateOrigem(it) },
            label = { Text("Origem *") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null && uiState.origem.isBlank()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Campo Destino
        OutlinedTextField(
            value = uiState.destino,
            onValueChange = { viewModel.updateDestino(it) },
            label = { Text("Destino *") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null && uiState.destino.isBlank()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Campo Data / Hora
        OutlinedTextField(
            value = uiState.dataHora,
            onValueChange = { viewModel.updateDataHora(it) },
            label = { Text("Data e Hora *") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null && uiState.dataHora.isBlank()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Número de Lugares
        OutlinedTextField(
            value = uiState.numLugares,
            onValueChange = { viewModel.updateNumLugares(it) },
            label = { Text("Número de Lugares Disponíveis *") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null && uiState.numLugares.isBlank()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Informações Carro
        OutlinedTextField(
            value = uiState.informacoesCarro,
            onValueChange = { viewModel.updateInformacoesCarro(it) },
            label = { Text("Informações do Carro *") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null && uiState.informacoesCarro.isBlank()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Upload Carta
        OutlinedTextField(
            value = uiState.uploadCarta,
            onValueChange = { viewModel.updateUploadCarta(it) },
            label = { Text("Upload Carta de Condução *") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null && uiState.uploadCarta.isBlank()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Preferências (Opcional - não marca erro)
        OutlinedTextField(
            value = uiState.preferencias,
            onValueChange = { viewModel.updatePreferencias(it) },
            label = { Text("Preferências (Opcional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Botão Criar
        Button(
            onClick = {
                viewModel.criarViagem {
                    navController.navigate(AppRoutes.CheckoutDriver.route)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text(text = "Criar!", color = Color.White, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
