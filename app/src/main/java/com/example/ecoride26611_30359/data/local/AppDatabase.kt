package com.example.ecoride26611_30359.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        TripEntity::class,
        UserEntity::class,
        ReservationEntity::class,
        ChatEntity::class,
        MessageEntity::class,
        CheckpointEntity::class
    ],
    version = 14,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tripDao(): TripDao
    abstract fun userDao(): UserDao
    abstract fun checkpointDao(): CheckpointDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        private val DB_SEED_CALLBACK = object : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                seed()
            }

            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                super.onDestructiveMigration(db)
                seed()
            }

            private fun seed() {
                CoroutineScope(Dispatchers.IO).launch {
                    INSTANCE?.checkpointDao()?.insertAll(CheckpointSeed.data)
                }
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ecoride_database"
                )
                    .addCallback(DB_SEED_CALLBACK)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
