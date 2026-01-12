package com.example.ecoride26611_30359.data.local

// Este modelo combina dados das duas tabelas
data class TripWithDriver(
    val id: Int,
    val userId: Int,
    val driverName: String, // Nome que vem da tabela users
    val origem: String,
    val destino: String,
    val dataHora: String
)
