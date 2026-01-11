package com.example.ecoride26611_30359.ui.screens.chat

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Representa uma mensagem individual na conversa
data class MessageEntity(
    val text: String,
    val isFromMe: Boolean,
    val timestamp: String
)

// Estado do ecrã de mensagens
data class MessagesUiState(
    val contactName: String = "Utilizador",
    val messages: List<MessageEntity> = emptyList(),
    val currentInput: String = ""
)

class MessagesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MessagesUiState())
    val uiState: StateFlow<MessagesUiState> = _uiState.asStateFlow()

    init {
        loadConversation()
    }

    private fun loadConversation() {
        // Dados estáticos iniciais
        val initialMessages = listOf(
            MessageEntity("Olá! Ainda tens lugar para a viagem?", false, "10:30"),
            MessageEntity("Olá! Sim, ainda tenho dois lugares disponíveis.", true, "10:32"),
            MessageEntity("Ótimo! Aceitas levar uma mala pequena?", false, "10:33")
        )
        _uiState.update { it.copy(messages = initialMessages, contactName = "Ana") }
    }

    fun onInputChange(newValue: String) {
        _uiState.update { it.copy(currentInput = newValue) }
    }

    fun sendMessage() {
        val textToSend = _uiState.value.currentInput
        if (textToSend.isNotBlank()) {
            val newMessage = MessageEntity(
                text = textToSend,
                isFromMe = true,
                timestamp = "10:35" // Numa app real usaríamos o tempo atual
            )

            _uiState.update {
                it.copy(
                    messages = it.messages + newMessage,
                    currentInput = "" // Limpa o campo de texto
                )
            }
        }
    }
}


