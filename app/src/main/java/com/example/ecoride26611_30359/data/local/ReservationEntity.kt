package com.example.ecoride26611_30359.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservations")
data class ReservationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,  // ID do passageiro que aceitou
    val tripId: Int   // ID da viagem aceite
)