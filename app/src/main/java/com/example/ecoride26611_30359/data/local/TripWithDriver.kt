package com.example.ecoride26611_30359.data.local

data class TripWithDriver(
    val id: Int,
    val userId: Int,
    val driverName: String,
    val origem: String,
    val destino: String,
    val dataHora: String,
    val lugaresDisponiveis: Int,
    // Adicionado para mostrar ao passageiro
    val carro: String = "",
    val matricula: String = ""
)