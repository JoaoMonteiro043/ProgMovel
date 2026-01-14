package com.example.ecoride26611_30359.ui.screens.driver

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ecoride26611_30359.navigation.AppRoutes

@Composable
fun CheckoutDriverScreen(
    navController: NavHostController,
    viewModel: CheckoutDriverViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Resumo da Viagem Criada", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Image(
                painter = painterResource(id = uiState.mapaImagem),
                contentDescription = "Mapa do percurso",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Itinerário", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))

                Text("De: ${uiState.origem}", fontWeight = FontWeight.SemiBold)
                Text("Para: ${uiState.destino}", fontWeight = FontWeight.SemiBold)
                // Adicionado resumo de lugares
                Text("Lugares para Passageiros: ${uiState.numLugares}", fontWeight = FontWeight.Bold, color = Color.DarkGray)

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Distância", color = Color.Gray, fontSize = 12.sp)
                        Text(uiState.distancia, fontWeight = FontWeight.Bold)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Tempo Estimado", color = Color.Gray, fontSize = 12.sp)
                        Text(uiState.tempoEstimado, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.weight(1f).height(50.dp)
            ) {
                Text("Editar")
            }

            Button(
                onClick = {
                    viewModel.confirmarViagem {
                        navController.navigate(AppRoutes.Home.route) {
                            popUpTo(AppRoutes.Home.route) { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.weight(1f).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Confirmar", color = Color.White)
            }
        }
    }
}