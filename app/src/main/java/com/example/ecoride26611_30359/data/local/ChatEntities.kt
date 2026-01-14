package com.example.ecoride26611_30359.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey val tripId: Int, // O ID do Chat é o mesmo ID da Viagem
    val groupName: String,
    val lastMessage: String = "Grupo criado!",
    val lastMessageTime: String = ""
)

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val chatId: Int,
    val senderId: Int,
    val senderName: String,
    val text: String,
    val timestamp: String,
    val isFromMe: Boolean = false // Usado apenas para lógica de UI
)