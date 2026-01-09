import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.weight

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel() // Injeção do ViewModel
) {
    // Observa o estado do ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ... (resto do layout igual)

        // Substitua os textos estáticos pelos valores do uiState:
        Text(text = uiState.userName, color = Color.DarkGray)

        // ...

        Text(text = uiState.userEmail, color = Color.DarkGray)

        // ...

        Text(
            text = "${uiState.userRating} estrelas",
            color = Color.DarkGray,
            modifier = Modifier.padding(start = 5.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        // Botão Logout atualizado
        Button(
            onClick = {
                viewModel.onLogout {
                    navController.navigate(AppRoutes.Login.route) {
                        popUpTo(0) // Limpa o histórico de navegação ao sair
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

        Spacer(modifier = Modifier.height(15.dp))
    }
}
