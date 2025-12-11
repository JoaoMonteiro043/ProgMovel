package com.example.ecoride26611_30359.ui.screens.driver

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ecoride26611_30359.R
import com.example.ecoride26611_30359.navigation.AppRoutes

@Composable
fun CheckoutDriverScreen(navController: NavHostController) {

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

        Image(
            painter = painterResource(id = R.drawable.map),
            contentDescription = "Mapa",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("15 mins")
            Text("25 km")
            Text("Chegada: 21:47")
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Informações do passageiro
        Text("Passageiro", fontWeight = FontWeight.Bold, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.girl),
            contentDescription = "Passageiro",
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(60.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text("Hora:", fontWeight = FontWeight.Bold)
                Text("21:26   |   25/11/2030")
            }
            Column {
                Text("Ponto de encontro:", fontWeight = FontWeight.Bold)
                Text("Parque da Cidade")
            }
        }

        Spacer(modifier = Modifier.height(60.dp))

        // Botão Detalhes Passageiro
        Button(
            onClick = { /* Pode abrir um popup futuramente */ },
            modifier = Modifier
                .width(200.dp)
                .height(45.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Text("Detalhes Passageiro", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(60.dp))

        // Botões Aceitar / Declinar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.navigate(AppRoutes.Payment.route) },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(end = 6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Accept", color = Color.White)
            }

            Button(
                onClick = { navController.navigate(AppRoutes.DashboardDriver.route) },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(start = 6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
            ) {
                Text("Decline", color = Color.White)
            }
        }
    }
}
