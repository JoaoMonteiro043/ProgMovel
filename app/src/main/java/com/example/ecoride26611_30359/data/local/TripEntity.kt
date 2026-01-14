package com.example.ecoride26611_30359.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val origem: String,
    val destino: String,
    val dataHora: String,
    val lugaresTotal: Int,
    val lugaresDisponiveis: Int,
    val latOrigem: Double = 0.0,
    val lngOrigem: Double = 0.0,
    val latDestino: Double = 0.0,
    val lngDestino: Double = 0.0
)