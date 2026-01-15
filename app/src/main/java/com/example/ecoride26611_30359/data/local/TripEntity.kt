package com.example.ecoride26611_30359.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,

    // agora só checkpoints
    val origemCheckpointId: Int,
    val destinoCheckpointId: Int,

    // nomes bonitos (só para UI)
    val origemLabel: String,
    val destinoLabel: String,

    val dataHora: String,
    val lugaresTotal: Int,
    val lugaresDisponiveis: Int
)
