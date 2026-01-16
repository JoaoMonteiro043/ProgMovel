package com.example.ecoride26611_30359.ui.screens.passenger

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
import com.example.ecoride26611_30359.R
import com.example.ecoride26611_30359.navigation.AppRoutes
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun CheckoutPassengerScreen(
    navController: NavHostController,
    loggedUserId: Int,
    tripId: Int,
    viewModel: CheckoutPassengerViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(loggedUserId) {
        viewModel.carregarDados(loggedUserId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Resumo da Reserva", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Image(
                painter = painterResource(id = uiState.mapaImagem),
                contentDescription = "Mapa",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text("Informação da Viagem", fontWeight = FontWeight.Bold, fontSize = 18.sp)

                Text("De: ${uiState.origem}", fontWeight = FontWeight.SemiBold)
                Text("Para: ${uiState.destino}", fontWeight = FontWeight.SemiBold)
                Text("Data: ${uiState.dataViagem}", color = Color.Gray)

                uiState.distanciaKm?.let {
                    Text(
                        text = "Distância: %.1f km".format(it),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
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
                    val encodedOri = URLEncoder.encode(uiState.origem, StandardCharsets.UTF_8.toString())
                    val encodedDest = URLEncoder.encode(uiState.destino, StandardCharsets.UTF_8.toString())
                    val encodedData = URLEncoder.encode(uiState.dataViagem, StandardCharsets.UTF_8.toString())

                    navController.navigate(
                        "${AppRoutes.Payment.route}?" +
                                "from=passenger&tripId=$tripId" +
                                "&origemLabel=$encodedOri" +
                                "&destinoLabel=$encodedDest" +
                                "&data=$encodedData"
                    )
                },
                enabled = !uiState.jaReservou,
                modifier = Modifier.weight(1f).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(
                    text = if (uiState.jaReservou) "Aceite" else "Pagar",
                    color = Color.White
                )
            }
        }
    }
}
