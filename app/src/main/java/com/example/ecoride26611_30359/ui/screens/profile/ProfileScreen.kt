package com.example.ecoride26611_30359.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    viewModel: ProfileViewModel = viewModel() // Injeção do ViewModel
) {
    // Observa o estado do ViewModel (userName, userEmail, userRating)
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Meu Perfil",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Linha do Nome
        InfoRow(label = "Nome", value = uiState.userName)

        Spacer(modifier = Modifier.height(20.dp))

        // Linha do Email
        InfoRow(label = "Email", value = uiState.userEmail)

        Spacer(modifier = Modifier.height(20.dp))

        // Linha da Avaliação
        InfoRow(label = "Avaliação", value = "${uiState.userRating} estrelas")

        // Este Spacer com weight(1f) empurra o botão para o fundo
        Spacer(modifier = Modifier.weight(1f))

        // Botão Logout
        Button(
            onClick = {
                viewModel.onLogout {
                    // Navega para o login e limpa todo o histórico para não voltar ao carregar "back"
                    navController.navigate(AppRoutes.Login.route) {
                        popUpTo(0)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("LOG OUT", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

/**
 * Componente auxiliar para as linhas de informação do perfil
 */
@Composable
fun InfoRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 14.sp, color = Color.Gray)
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray
        )
    }
}
