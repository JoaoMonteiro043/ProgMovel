package com.example.ecoride26611_30359.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checkpoints")
data class CheckpointEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val lat: Double,
    val lng: Double
)
