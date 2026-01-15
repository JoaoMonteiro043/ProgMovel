package com.example.ecoride26611_30359.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {

    @Insert
    suspend fun insertTrip(trip: TripEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: ChatEntity)

    @Insert
    suspend fun insertReservation(reservation: ReservationEntity)

    @Insert
    suspend fun insertMessage(message: MessageEntity)

    @Query("DELETE FROM trips WHERE id = :tripId")
    suspend fun deleteTrip(tripId: Int)

    @Query("DELETE FROM reservations WHERE tripId = :tripId")
    suspend fun deleteReservationsByTrip(tripId: Int)

    @Query("DELETE FROM chats WHERE tripId = :tripId")
    suspend fun deleteChatByTrip(tripId: Int)

    @Query("DELETE FROM messages WHERE chatId = :tripId")
    suspend fun deleteMessagesByTrip(tripId: Int)

    @Transaction
    suspend fun cancelEntireTrip(tripId: Int) {
        deleteMessagesByTrip(tripId)
        deleteChatByTrip(tripId)
        deleteReservationsByTrip(tripId)
        deleteTrip(tripId)
    }

    // CHAT
    @Query("""
        SELECT DISTINCT chats.* FROM chats 
        INNER JOIN trips ON chats.tripId = trips.id
        LEFT JOIN reservations ON trips.id = reservations.tripId
        WHERE trips.userId = :userId OR reservations.userId = :userId
    """)
    fun getChatsForUser(userId: Int): Flow<List<ChatEntity>>

    @Query("SELECT * FROM messages WHERE chatId = :chatId ORDER BY id ASC")
    fun getMessagesForChat(chatId: Int): Flow<List<MessageEntity>>

    @Query("""
        SELECT users.* FROM users 
        INNER JOIN trips ON users.id = trips.userId 
        WHERE trips.id = :tripId
        UNION
        SELECT users.* FROM users 
        INNER JOIN reservations ON users.id = reservations.userId 
        WHERE reservations.tripId = :tripId
    """)
    suspend fun getTripParticipants(tripId: Int): List<UserEntity>

    @Query("SELECT COUNT(*) FROM reservations WHERE userId = :userId AND tripId = :tripId")
    suspend fun hasUserReservedTrip(userId: Int, tripId: Int): Int

    // PASSAGEIRO (sem texto)
    @Query("""
        SELECT 
            trips.id,
            trips.userId,
            users.name AS driverName,
            trips.origemLabel,
            trips.destinoLabel,
            trips.dataHora,
            trips.lugaresDisponiveis,
            users.carro,
            users.matricula
        FROM trips
        INNER JOIN users ON trips.userId = users.id
        WHERE trips.lugaresDisponiveis > 0
          AND trips.userId != :currentUserId
    """)
    fun getAvailableTripsForPassenger(currentUserId: Int): Flow<List<TripWithDriver>>

    // DETALHE DA VIAGEM
    @Query("""
        SELECT 
            trips.id,
            trips.userId,
            users.name AS driverName,
            trips.origemLabel,
            trips.destinoLabel,
            trips.dataHora,
            trips.lugaresDisponiveis,
            users.carro,
            users.matricula
        FROM trips
        INNER JOIN users ON trips.userId = users.id
        WHERE trips.id = :tripId LIMIT 1
    """)
    suspend fun getTripWithDriverById(tripId: Int): TripWithDriver?

    @Query("UPDATE trips SET lugaresDisponiveis = lugaresDisponiveis - 1 WHERE id = :tripId AND lugaresDisponiveis > 0")
    suspend fun reserveSeat(tripId: Int)

    @Query("UPDATE trips SET lugaresDisponiveis = lugaresDisponiveis + 1 WHERE id = :tripId")
    suspend fun addSeatBack(tripId: Int)

    @Query("DELETE FROM reservations WHERE userId = :userId AND tripId = :tripId")
    suspend fun removeReservation(userId: Int, tripId: Int)

    @Query("SELECT * FROM trips ORDER BY id DESC LIMIT 1")
    fun getLastTrip(): Flow<TripEntity?>
}
