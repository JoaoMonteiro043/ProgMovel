package com.example.ecoride26611_30359.ui.screens.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ecoride26611_30359.R
import com.example.ecoride26611_30359.navigation.AppRoutes

data class ChatMessage(
    val name: String,
    val time: String,
    val messagePreview: String,
    val image: Int
)

@Composable
fun ChatScreen(navController: NavHostController) {

    val messages = listOf(
        ChatMessage("Ana", "1d", "Temos de combinar um café", R.drawable.girl),
        ChatMessage("Miguel", "1d", "A que horas começa?", R.drawable.boy),
        ChatMessage("Joana", "2d", "Feliz aniversário!!! 🎉", R.drawable.girl),
        ChatMessage("Inês", "3d", "A Rita não pode vir?", R.drawable.girl),
        ChatMessage("Luís", "4d", "Vou em setembro. E você?", R.drawable.boy),
        ChatMessage("Nuno", "5d", "Votaste no Chega? A sério..", R.drawable.boy)
    )

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {

        Text(
            text = "Mensagens",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        LazyColumn {
            items(messages) { message ->
                ChatItem(message)
                Divider(color = Color.LightGray.copy(alpha = 0.3f))
            }
        }
    }
}

@Composable
fun ChatItem(message: ChatMessage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable { }
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
            Text(message.messagePreview, fontSize = 14.sp, color = Color.DarkGray)
        }

    }
}
