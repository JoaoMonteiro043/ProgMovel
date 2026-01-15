package com.example.ecoride26611_30359.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ecoride26611_30359.data.local.MessageEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(
    navController: NavHostController,
    loggedUserId: Int,
    viewModel: MessagesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDetails by remember { mutableStateOf(false) }
    var showParticipants by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    if (showDetails && uiState.tripDetails != null) {
        val isDriver = loggedUserId == uiState.tripDetails?.userId

        AlertDialog(
            onDismissRequest = { showDetails = false },
            confirmButton = {
                TextButton(onClick = { showDetails = false }) { Text("Fechar") }
            },
            dismissButton = {
                if (isDriver) {
                    TextButton(onClick = { viewModel.cancelTrip { showDetails = false; navController.popBackStack() } }) {
                        Text("CANCELAR VIAGEM", color = Color.Red, fontWeight = FontWeight.Bold)
                    }
                } else {
                    TextButton(onClick = { viewModel.leaveTrip(loggedUserId) { showDetails = false; navController.popBackStack() } }) {
                        Text("Sair da Viagem", color = MaterialTheme.colorScheme.error)
                    }
                }
            },
            title = { Text("Resumo da Boleia") },
            text = {
                Column {
                    Text("De: ${uiState.tripDetails?.origemLabel}", fontWeight = FontWeight.Bold)
                    Text("Para: ${uiState.tripDetails?.destinoLabel}", fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    if (showParticipants) {
        ModalBottomSheet(onDismissRequest = { showParticipants = false }, sheetState = sheetState) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp).padding(bottom = 32.dp)) {
                Text("Participantes", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                uiState.participants.forEach { user ->
                    ListItem(headlineContent = { Text(user.name) })
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(modifier = Modifier.clickable { viewModel.loadParticipants(); showParticipants = true }) {
                        Text(uiState.groupName, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                        Text("Ver participantes", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { showDetails = true }) {
                        Icon(Icons.Default.Info, contentDescription = "Detalhes")
                    }
                }
            )
        },
        bottomBar = {
            Surface(tonalElevation = 8.dp) {
                Row(modifier = Modifier.fillMaxWidth().padding(8.dp).navigationBarsPadding().imePadding(), verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = uiState.currentInput,
                        onValueChange = { viewModel.onInputChange(it) },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Mensagem...") }
                    )
                    IconButton(onClick = { viewModel.sendMessage(loggedUserId) }) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar")
                    }
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(uiState.messages) { message ->
                    MessageBubble(message, loggedUserId)
                }
            }
        }
    }
}

@Composable
fun MessageBubble(message: MessageEntity, loggedUserId: Int) {
    val isMe = message.senderId == loggedUserId
    Box(modifier = Modifier.fillMaxWidth().padding(8.dp), contentAlignment = if (isMe) Alignment.CenterEnd else Alignment.CenterStart) {
        Surface(color = if (isMe) MaterialTheme.colorScheme.primary else Color.LightGray, shape = RoundedCornerShape(8.dp)) {
            Text(message.text, modifier = Modifier.padding(8.dp), color = if (isMe) Color.White else Color.Black)
        }
    }
}