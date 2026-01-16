package com.example.ecoride26611_30359.ui.screens.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
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
fun ChatScreen(
    navController: NavHostController,
    userId: Int,
    viewModel: ChatViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(userId) {
        viewModel.loadUserChats(userId)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        Text("Mensagens", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.groups.isEmpty()) {
            Text("Não tem conversas ativas.", color = Color.Gray)
        } else {
            LazyColumn {
                items(uiState.groups) { chat ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("${AppRoutes.Messages.route}/${chat.tripId}")
                            }
                            .padding(vertical = 12.dp)
                    ) {
                        Icon(Icons.Default.Groups, null, Modifier.size(40.dp))
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(chat.groupName, fontWeight = FontWeight.Bold)

                            if (chat.driverCar.isNotBlank()) {
                                Text(
                                    "${chat.driverName}      ${chat.dataHora}",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }

                        }
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}
