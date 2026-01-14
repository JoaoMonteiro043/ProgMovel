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
import androidx.compose.material.icons.filled.Person
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

    // Dialog de Detalhes da Viagem
    if (showDetails && uiState.tripDetails != null) {
        AlertDialog(
            onDismissRequest = { showDetails = false },
            confirmButton = {
                TextButton(onClick = { showDetails = false }) { Text("Fechar") }
            },
            dismissButton = {
                if (loggedUserId != uiState.tripDetails?.userId) {
                    TextButton(
                        onClick = {
                            viewModel.leaveTrip(loggedUserId) {
                                showDetails = false
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Text("Sair da Viagem", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                    }
                }
            },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.DirectionsCar, null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(8.dp))
                    Text("Resumo da Boleia", fontSize = 20.sp)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("ITINERÁRIO", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                            Text("De: ${uiState.tripDetails?.origem}", fontWeight = FontWeight.Bold)
                            Text("Para: ${uiState.tripDetails?.destino}", fontWeight = FontWeight.Bold)
                        }
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text("CONDUTOR", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                            Text(uiState.tripDetails?.driverName ?: "", fontWeight = FontWeight.Medium)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("HORÁRIO", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                            Text(uiState.tripDetails?.dataHora ?: "", fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        )
    }

    // Modal de Participantes
    if (showParticipants) {
        ModalBottomSheet(onDismissRequest = { showParticipants = false }, sheetState = sheetState) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp).padding(bottom = 32.dp)) {
                Text("Participantes no Grupo", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                uiState.participants.forEach { user ->
                    ListItem(
                        headlineContent = {
                            val label = if (user.id == uiState.tripDetails?.userId) "${user.name} (Condutor)" else user.name
                            Text(label)
                        },
                        leadingContent = {
                            Box(Modifier.size(42.dp).clip(CircleShape).background(MaterialTheme.colorScheme.secondaryContainer), contentAlignment = Alignment.Center) {
                                Text(user.name.take(1).uppercase(), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer)
                            }
                        }
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.shadow(4.dp),
                title = {
                    Column(modifier = Modifier.clickable {
                        viewModel.loadParticipants()
                        showParticipants = true
                    }) {
                        Text(uiState.groupName, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                        Text("Ver participantes • Resumo", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { showDetails = true }) {
                        Icon(Icons.Default.Info, contentDescription = "Detalhes", tint = MaterialTheme.colorScheme.primary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        bottomBar = {
            Surface(tonalElevation = 8.dp, shadowElevation = 8.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .navigationBarsPadding()
                        .imePadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = uiState.currentInput,
                        onValueChange = { viewModel.onInputChange(it) },
                        placeholder = { Text("Mensagem...") },
                        modifier = Modifier.weight(1f).clip(RoundedCornerShape(28.dp)),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        maxLines = 4
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    FloatingActionButton(
                        onClick = { viewModel.sendMessage(loggedUserId) },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White,
                        modifier = Modifier.size(48.dp),
                        shape = CircleShape,
                        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar", modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    ) { paddingValues ->
        // Fundo com cor leve para destacar bolhas
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
            ) {
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
    val alignment = if (isMe) Alignment.CenterEnd else Alignment.CenterStart

    // Cores de bolha modernas
    val bubbleColor = if (isMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val contentColor = if (isMe) Color.White else MaterialTheme.colorScheme.onSurface

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = alignment) {
        Column(
            horizontalAlignment = if (isMe) Alignment.End else Alignment.Start,
            modifier = Modifier.widthIn(max = 300.dp)
        ) {
            if (!isMe) {
                Text(
                    text = message.senderName,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 8.dp, bottom = 2.dp)
                )
            }
            Surface(
                color = bubbleColor,
                tonalElevation = if (isMe) 0.dp else 2.dp,
                shadowElevation = 1.dp,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isMe) 16.dp else 2.dp,
                    bottomEnd = if (isMe) 2.dp else 16.dp
                )
            ) {
                Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                    Text(
                        text = message.text,
                        color = contentColor,
                        fontSize = 15.sp,
                        lineHeight = 20.sp
                    )
                    Text(
                        text = message.timestamp,
                        fontSize = 10.sp,
                        color = if (isMe) Color.White.copy(alpha = 0.7f) else Color.Gray,
                        modifier = Modifier.align(Alignment.End).padding(top = 2.dp)
                    )
                }
            }
        }
    }
}