package com.example.ecoride26611_30359.ui.screens.passenger

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ecoride26611_30359.navigation.AppRoutes

@Composable
fun CheckoutPassengerScreen(
    navController: NavHostController,
    viewModel: CheckoutPassengerViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Title
        Text(
            text = "Checkout / Detalhes da Viagem",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // MAP
        Image(
            painter = painterResource(id = uiState.mapaImagem),
            contentDescription = "Mapa",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(uiState.tempoEstimado)
            Text(uiState.distancia)
            Text("Chegada: ${uiState.horaChegada}")
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text("Condutor: ${uiState.nomeCondutor}", fontWeight = FontWeight.Bold, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = uiState.fotoCondutor),
            contentDescription = "Condutor",
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text("Hora:", fontWeight = FontWeight.Bold)
                Text("${uiState.horaPartida}   |   ${uiState.dataViagem}")
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("Ponto de encontro:", fontWeight = FontWeight.Bold)
                Text(uiState.pontoEncontro)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { /* popup futuro com info do condutor */ },
            modifier = Modifier
                .width(200.dp)
                .height(45.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Text("Detalhes Condutor", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    viewModel.acceptTrip {
                        navController.navigate(AppRoutes.Payment.route)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Accept", color = Color.White)
            }

            Button(
                onClick = {
                    viewModel.declineTrip {
                        navController.navigate(AppRoutes.DashboardPassenger.route)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
            ) {
                Text("Decline", color = Color.White)
            }
        }
    }
}
