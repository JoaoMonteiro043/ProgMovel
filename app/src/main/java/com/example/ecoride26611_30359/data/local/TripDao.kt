package com.example.ecoride26611_30359.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {
    @Insert
    suspend fun insertTrip(trip: TripEntity)

    // Esta Query procura a viagem e junta o nome do utilizador (condutor)
    @Query("""
        SELECT trips.id, trips.userId, users.name as driverName, trips.origem, trips.destino, trips.dataHora 
        FROM trips 
        INNER JOIN users ON trips.userId = users.id
    """)
    fun getAllTripsWithDrivers(): Flow<List<TripWithDriver>>

    @Query("""
        SELECT trips.id, trips.userId, users.name as driverName, trips.origem, trips.destino, trips.dataHora 
        FROM trips 
        INNER JOIN users ON trips.userId = users.id 
        WHERE trips.id = :tripId LIMIT 1
    """)
    suspend fun getTripWithDriverById(tripId: Int): TripWithDriver?

    @Query("SELECT * FROM trips ORDER BY id DESC LIMIT 1")
    fun getLastTrip(): Flow<TripEntity?>
}
