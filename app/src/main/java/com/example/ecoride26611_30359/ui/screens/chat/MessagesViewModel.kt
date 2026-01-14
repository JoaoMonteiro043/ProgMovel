package com.example.ecoride26611_30359.ui.screens.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.ecoride26611_30359.data.local.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

data class MessagesUiState(
    val groupName: String = "Chat da Viagem",
    val messages: List<MessageEntity> = emptyList(),
    val currentInput: String = "",
    val tripDetails: TripWithDriver? = null,
    val participants: List<UserEntity> = emptyList()
)

class MessagesViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val tripDao = database.tripDao()
    private val userDao = database.userDao()
    private val chatId: Int = savedStateHandle.get<Int>("tripId") ?: -1

    private val _uiState = MutableStateFlow(MessagesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadMessages()
        loadTripDetails()
        loadParticipants()
    }

    private fun loadMessages() {
        if (chatId == -1) return
        viewModelScope.launch {
            tripDao.getMessagesForChat(chatId).collect { list ->
                _uiState.update { it.copy(messages = list) }
            }
        }
    }

    private fun loadTripDetails() {
        if (chatId == -1) return
        viewModelScope.launch {
            val trip = tripDao.getTripWithDriverById(chatId)
            _uiState.update { it.copy(tripDetails = trip, groupName = "Viagem: ${trip?.origem} - ${trip?.destino}") }
        }
    }

    fun loadParticipants() {
        viewModelScope.launch {
            val list = tripDao.getTripParticipants(chatId)
            _uiState.update { it.copy(participants = list) }
        }
    }

    fun onInputChange(v: String) = _uiState.update { it.copy(currentInput = v) }

    fun sendMessage(senderId: Int) {
        val text = _uiState.value.currentInput
        if (text.isBlank() || senderId == -1) return

        viewModelScope.launch {
            val user = userDao.getUserById(senderId)
            val senderName = user?.name ?: "Desconhecido"

            tripDao.insertMessage(MessageEntity(
                chatId = chatId,
                senderId = senderId,
                senderName = senderName,
                text = text,
                timestamp = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            ))
            _uiState.update { it.copy(currentInput = "") }
        }
    }

    fun leaveTrip(userId: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            tripDao.removeReservation(userId, chatId)
            tripDao.addSeatBack(chatId)
            onSuccess()
        }
    }
}