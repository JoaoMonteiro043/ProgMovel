package com.example.ecoride26611_30359.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        TripEntity::class,
        UserEntity::class,
        ReservationEntity::class,
        ChatEntity::class,
        MessageEntity::class
    ],
    version = 6, // Versão 6 para incluir Chat e Mensagens
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ecoride_database"
                )
                    .fallbackToDestructiveMigration() // Importante para não crashar ao mudar versão
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}