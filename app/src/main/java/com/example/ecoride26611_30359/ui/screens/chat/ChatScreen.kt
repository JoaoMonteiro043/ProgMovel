package com.example.ecoride26611_30359.ui.screens.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ecoride26611_30359.R
import com.example.ecoride26611_30359.navigation.AppRoutes // Importante para as rotas

// Modelo de dados
data class ChatMessage(
    val name: String,
    val time: String,
    val messagePreview: String,
    val image: Int
)

@Composable
fun ChatScreen(
    navController: NavHostController,
    viewModel: ChatViewModel = viewModel()
) {
    // Observa o estado vindo do ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {

        Text(
            text = "Mensagens",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn {
                items(uiState.messages) { message ->
                    ChatItem(message) {
                        // CORREÇÃO: Chama o navController para ir para o ecrã de mensagens
                        navController.navigate(AppRoutes.Messages.route)
                    }
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                }
            }
        }
    }
}

@Composable
fun ChatItem(message: ChatMessage, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable { onClick() } // O clique aqui dispara a navegação acima
    ) {
        // FOTO
        Image(
            painter = painterResource(id = message.image),
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(10.dp))

        // TEXTOS
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(message.name, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(6.dp))
                Text(message.time, fontSize = 12.sp, color = Color.Gray)
            }
            Text(
                text = message.messagePreview,
                fontSize = 14.sp,
                color = Color.DarkGray,
                maxLines = 1
            )
        }
    }
}
