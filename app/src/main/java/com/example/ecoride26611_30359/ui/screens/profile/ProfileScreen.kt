package com.example.ecoride26611_30359.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun ProfileScreen(
    navController: NavHostController,
    userId: Int,
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var carta by remember(uiState.carta) { mutableStateOf(uiState.carta) }
    var carro by remember(uiState.carro) { mutableStateOf(uiState.carro) }
    var matricula by remember(uiState.matricula) { mutableStateOf(uiState.matricula) }

    LaunchedEffect(userId) {
        if (userId != -1) viewModel.loadUserProfile(userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(top = 40.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Meu Perfil", fontSize = 26.sp, fontWeight = FontWeight.Bold)

        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 40.dp))
        } else {
            Spacer(Modifier.height(30.dp))

            uiState.errorMessage?.let {
                Text(it, color = Color.Red, fontSize = 14.sp)
                Spacer(Modifier.height(8.dp))
            }

            InfoRow(label = "Nome", value = uiState.userName)
            Spacer(Modifier.height(15.dp))
            InfoRow(label = "Email", value = uiState.userEmail)

            HorizontalDivider(Modifier.padding(vertical = 20.dp))

            Text("Veículo & Documentação", fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start))

            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = carta,
                onValueChange = { carta = it },
                label = { Text("Carta de Condução") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = carro,
                onValueChange = { carro = it },
                label = { Text("Modelo do Carro") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = matricula,
                onValueChange = { matricula = it },
                label = { Text("Matrícula") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { viewModel.updateProfile(userId, carta, carro, matricula) },
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Guardar Alterações")
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = { viewModel.onLogout { navController.navigate(AppRoutes.Login.route) { popUpTo(0) } } },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("LOG OUT", color = Color.White)
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 12.sp, color = Color.Gray)
        Text(text = value, fontSize = 17.sp, fontWeight = FontWeight.Medium)
    }
}