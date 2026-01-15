package com.example.ecoride26611_30359.ui.screens.home

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoride26611_30359.data.local.AppDatabase
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()

    var userName by mutableStateOf("Utilizador")
        private set

    fun carregarUsuario(userId: Int) {
        if (userId == -1) return
        viewModelScope.launch {
            val user = userDao.getUserById(userId)
            user?.let {
                userName = it.name
            }
        }
    }

    fun onNavigateToDriver(onNavigate: () -> Unit) {
        onNavigate()
    }

    fun onNavigateToPassenger(onNavigate: () -> Unit) {
        onNavigate()
    }
}