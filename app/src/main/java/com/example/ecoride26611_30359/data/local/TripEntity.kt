package com.example.ecoride26611_30359.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,

    //só os checkpoints
    val origemCheckpointId: Int,
    val destinoCheckpointId: Int,


    val origemLabel: String,
    val destinoLabel: String,

    val dataHora: String,
    //val horaPartida: String,
    val lugaresTotal: Int,
    val lugaresDisponiveis: Int
)
