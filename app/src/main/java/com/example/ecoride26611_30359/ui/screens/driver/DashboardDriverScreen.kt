package com.example.ecoride26611_30359.ui.screens.driver

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ecoride26611_30359.navigation.AppRoutes
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable


@Composable
fun DashboardDriverScreen(navController: NavHostController) {

    var origem by remember { mutableStateOf("") }
    var destino by remember { mutableStateOf("") }
    var dataHora by remember { mutableStateOf("") }
    var numLugares by remember { mutableStateOf("") }
    var informacoesCarro by remember { mutableStateOf("") }
    var uploadCarta by remember { mutableStateOf("") }
    var preferencias by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

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

        Spacer(modifier = Modifier.height(50.dp))

        // Campo Origem
        OutlinedTextField(
            value = origem,
            onValueChange = { origem = it },
            label = { Text("Origem") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Campo Destino
        OutlinedTextField(
            value = destino,
            onValueChange = { destino = it },
            label = { Text("Destino") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Campo Data / Hora
        OutlinedTextField(
            value = dataHora,
            onValueChange = { dataHora = it },
            label = { Text("Data e Hora") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Número de Lugares
        OutlinedTextField(
            value = numLugares,
            onValueChange = { numLugares = it },
            label = { Text("Número de Lugares Disponíveis") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Informações Carro
        OutlinedTextField(
            value = informacoesCarro,
            onValueChange = { informacoesCarro = it },
            label = { Text("Informações do Carro") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Upload Carta
        OutlinedTextField(
            value = uploadCarta,
            onValueChange = { uploadCarta = it },
            label = { Text("Upload Carta de Condução") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Preferências
        OutlinedTextField(
            value = preferencias,
            onValueChange = { preferencias = it },
            label = { Text("Preferências") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(50.dp))

        // Botão Criar
        Button(
            onClick = { navController.navigate(AppRoutes.CheckoutDriver.route)},
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text(text = "Criar!", color = Color.White, fontSize = 18.sp)

        }
    }
}
