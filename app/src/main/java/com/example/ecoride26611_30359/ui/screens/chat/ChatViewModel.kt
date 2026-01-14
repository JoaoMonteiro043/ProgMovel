package com.example.ecoride26611_30359.ui.screens.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoride26611_30359.data.local.AppDatabase
import com.example.ecoride26611_30359.data.local.ChatEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ChatUiState(
    val groups: List<ChatEntity> = emptyList(),
    val isLoading: Boolean = true
)

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    private val tripDao = AppDatabase.getDatabase(application).tripDao()
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()

    fun loadUserChats(userId: Int) {
        viewModelScope.launch {
            tripDao.getChatsForUser(userId).collect { chats ->
                _uiState.update { it.copy(groups = chats, isLoading = false) }
            }
        }
    }
}