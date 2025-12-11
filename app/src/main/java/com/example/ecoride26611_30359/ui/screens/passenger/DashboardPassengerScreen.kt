package com.example.ecoride26611_30359.ui.screens.passenger

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
fun DashboardPassengerScreen(navController: NavHostController) {

    var filters by remember { mutableStateOf("") }
    var preferences by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        // Top Bar + Back Button
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

        // Campo ORIGEM
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable { }
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Origem", modifier = Modifier.weight(1f))
            Text("Pesquisar origem", color = Color.Gray)
        }

        Divider()

        // Campo DESTINO
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable { }
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Destino", modifier = Modifier.weight(1f))
            Text("Pesquisar destino", color = Color.Gray)
        }

        Divider()

        // Campo DATA E HORA (Fixo Simples)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable { }
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Data e Hora", modifier = Modifier.weight(1f))
            Text("12:00   25/11/2030", color = Color.Gray)
        }

        Divider()

        Spacer(modifier = Modifier.height(20.dp))

        // Filtros Opcionais
        Text("Filtros Opcionais")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(vertical = 6.dp)
                .background(Color(0xFFDDDDDD), RoundedCornerShape(6.dp))
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Preferências
        Text("Preferências")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(vertical = 6.dp)
                .background(Color(0xFFDDDDDD), RoundedCornerShape(6.dp))
        )

        Spacer(modifier = Modifier.height(250.dp))

        // Botão Procurar
        Button(
            onClick = {
                navController.navigate(AppRoutes.CheckoutPassenger.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Procurar!", color = Color.White, fontSize = 18.sp)
        }
    }
}
