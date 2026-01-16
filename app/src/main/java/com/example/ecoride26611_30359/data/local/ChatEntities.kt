package com.example.ecoride26611_30359.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tripId: Int,
    val groupName: String,
    val driverName: String = "",
    val driverCar: String = "",
    val driverPlate: String = ""
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