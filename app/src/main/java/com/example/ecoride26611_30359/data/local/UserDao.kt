package com.example.ecoride26611_30359.data.local

import androidx.room.*

@Dao
interface UserDao {
    // A estratégia REPLACE garante que se o ID já existir, ele atualiza os dados em vez de dar crash
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int): UserEntity?
}