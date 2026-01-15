package com.example.ecoride26611_30359.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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

@Composable
fun HomeScreen(
    navController: NavHostController,
    userId: Int,
    viewModel: HomeViewModel = viewModel()
) {
    // Estado local para controlar a visibilidade do aviso
    var showProfileError by remember { mutableStateOf(false) }

    LaunchedEffect(userId) {
        viewModel.carregarUsuario(userId)
    }

    // Alerta de Erro de Perfil
    if (showProfileError) {
        AlertDialog(
            onDismissRequest = { showProfileError = false },
            title = { Text("Perfil Incompleto") },
            text = { Text("Para criar uma viagem, deve primeiro registar a sua Carta de Condução e o seu Veículo no Perfil.") },
            confirmButton = {
                Button(
                    onClick = {
                        showProfileError = false
                        navController.navigate(AppRoutes.Profile.route)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("Ir para Perfil")
                }
            },
            dismissButton = {
                TextButton(onClick = { showProfileError = false }) {
                    Text("Cancelar", color = Color.Gray)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "BEM-VINDO,",
            fontSize = 20.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = viewModel.userName.uppercase(),
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(80.dp))

        // CARD CRIAR VIAGEM (Com validação)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clickable {
                    if (viewModel.canCreateTrip) {
                        navController.navigate(AppRoutes.DashboardDriver.route)
                    } else {
                        showProfileError = true
                    }
                },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hands),
                    contentDescription = "Criar Viagem",
                    modifier = Modifier.size(110.dp),
                    contentScale = ContentScale.Fit
                )
                Text(text = "Criar Viagem", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clickable {
                    navController.navigate(AppRoutes.DashboardPassenger.route)
                },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ride_search),
                    contentDescription = "Procurar Viagem",
                    modifier = Modifier.size(110.dp),
                    contentScale = ContentScale.Fit
                )
                Text(text = "Procurar Viagem", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
