package com.example.ecoride26611_30359.data.local

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object CheckpointInitializer {

    fun init(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val dao = AppDatabase.getDatabase(context).checkpointDao()
            if (dao.getAll().isEmpty()) {
                dao.insertAll(CheckpointSeed.data)
            }
        }
    }
}