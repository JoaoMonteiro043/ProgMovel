package com.example.ecoride26611_30359.ui.screens.achievements

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Estado que representa o que o ecrã deve mostrar.
 */
data class AchievementsUiState(
    val achievements: List<Achievement> = emptyList(),
    val isLoading: Boolean = true
)

class AchievementsViewModel : ViewModel() {

    // _uiState é privado para que apenas o ViewModel possa alterar os dados
    private val _uiState = MutableStateFlow(AchievementsUiState())
    // uiState é público para a UI observar
    val uiState: StateFlow<AchievementsUiState> = _uiState.asStateFlow()

    init {
        fetchAchievements()
    }

    private fun fetchAchievements() {
        // Simulando um carregamento (poderia vir de uma base de dados ou API)
        val data = listOf(
            Achievement(1, "Primeira Viagem", "Complete a sua primeira viagem como passageiro.", Icons.Default.Star, 1, 1),
            Achievement(2, "Condutor Novato", "Complete a sua primeira viagem como condutor.", Icons.Default.MilitaryTech, 1, 1),
            Achievement(3, "Viajante Frequente", "Complete 10 viagens.", Icons.Default.WorkspacePremium, 7, 10),
            Achievement(4, "Perfil Completo", "Preencha todas as informações do seu perfil.", Icons.Default.VerifiedUser, 0, 1),
            Achievement(5, "Motorista 5 Estrelas", "Receba uma avaliação de 5 estrelas.", Icons.Default.Star, 1, 1),
            Achievement(6, "Rei da Estrada", "Complete 50 viagens como condutor.", Icons.Default.MilitaryTech, 34, 50)
        )

        _uiState.value = AchievementsUiState(
            achievements = data,
            isLoading = false
        )
    }
}
