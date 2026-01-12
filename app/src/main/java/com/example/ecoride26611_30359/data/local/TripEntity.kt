package com.example.ecoride26611_30359.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int, // ID do utilizador que criou a viagem
    val origem: String,
    val destino: String,
    val dataHora: String,
    val latOrigem: Double,
    val lngOrigem: Double,
    val latDestino: Double,
    val lngDestino: Double
)
