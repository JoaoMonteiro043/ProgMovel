package com.example.ecoride26611_30359.ui.screens.chat

import androidx.lifecycle.ViewModel
import com.example.ecoride26611_30359.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Estado do ecrã de Chat
data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = true
)

class ChatViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        loadMessages()
    }

    private fun loadMessages() {
        // Simulação de carregamento de dados
        val sampleMessages = listOf(
            ChatMessage("Ana", "1d", "Temos de combinar um café", R.drawable.girl),
            ChatMessage("Miguel", "1d", "A que horas começa?", R.drawable.boy),
            ChatMessage("Joana", "2d", "Feliz aniversário!!! 🎉", R.drawable.girl),
            ChatMessage("Inês", "3d", "A Rita não pode vir?", R.drawable.girl),
            ChatMessage("Luís", "4d", "Vou em setembro. E você?", R.drawable.boy),
            ChatMessage("Nuno", "5d", "Votaste no Chega? A sério..", R.drawable.boy)
        )

        _uiState.value = ChatUiState(
            messages = sampleMessages,
            isLoading = false
        )
    }
}


