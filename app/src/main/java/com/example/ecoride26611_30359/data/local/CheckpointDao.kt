package com.example.ecoride26611_30359.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CheckpointDao {

    @Query("SELECT * FROM checkpoints ORDER BY id")
    suspend fun getAll(): List<CheckpointEntity>

    @Query("SELECT * FROM checkpoints WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): CheckpointEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<CheckpointEntity>)
}


