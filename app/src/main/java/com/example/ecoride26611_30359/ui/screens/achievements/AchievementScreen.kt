package com.example.ecoride26611_30359.ui.screens.achievements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MilitaryTech // Ícone para conquistas
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecoride26611_30359.ui.EcoRide26611_30359Theme

// 1. Modelo de dados para representar uma Conquista
data class Achievement(
    val id: Int,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val currentProgress: Int,
    val targetProgress: Int
) {
    val isUnlocked: Boolean = currentProgress >= targetProgress
}

// 2. Lista de exemplo de conquistas (pode vir de uma base de dados ou API no futuro)
val sampleAchievements = listOf(
    Achievement(1, "Primeira Viagem", "Complete a sua primeira viagem como passageiro.", Icons.Default.Star, 1, 1),
    Achievement(2, "Condutor Novato", "Complete a sua primeira viagem como condutor.", Icons.Default.MilitaryTech, 1, 1),
    Achievement(3, "Viajante Frequente", "Complete 10 viagens.", Icons.Default.WorkspacePremium, 7, 10),
    Achievement(4, "Perfil Completo", "Preencha todas as informações do seu perfil.", Icons.Default.VerifiedUser, 0, 1),
    Achievement(5, "Motorista 5 Estrelas", "Receba uma avaliação de 5 estrelas.", Icons.Default.Star, 1, 1),
    Achievement(6, "Rei da Estrada", "Complete 50 viagens como condutor.", Icons.Default.MilitaryTech, 34, 50)
)


// 3. O ecrã principal de Conquistas
@Composable
fun AchievementsScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título da página
            item {
                Text(
                    text = "As Suas Conquistas",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Lista de conquistas
            items(sampleAchievements) { achievement ->
                AchievementItem(achievement = achievement)
            }
        }
    }
}

// 4. O Composable para um item individual na lista
@Composable
fun AchievementItem(achievement: Achievement) {
    val iconColor = if (achievement.isUnlocked) MaterialTheme.colorScheme.primary else Color.Gray
    val backgroundColor = if (achievement.isUnlocked) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surfaceVariant

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone da Conquista
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(iconColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = achievement.icon,
                    contentDescription = achievement.title,
                    tint = iconColor,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Textos e Barra de Progresso
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = achievement.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = achievement.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Barra de progresso (só aparece se a conquista não estiver desbloqueada)
                if (!achievement.isUnlocked) {
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { achievement.currentProgress.toFloat() / achievement.targetProgress.toFloat() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }
    }
}

// 5. Preview para ver o design no Android Studio
@Preview(showBackground = true)
@Composable
fun AchievementsScreenPreview() {
    EcoRide26611_30359Theme {
        AchievementsScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun AchievementItemPreview() {
    EcoRide26611_30359Theme {
        Column {
            AchievementItem(achievement = sampleAchievements[0]) // Desbloqueada
            Spacer(Modifier.height(10.dp))
            AchievementItem(achievement = sampleAchievements[2]) // Em progresso
        }
    }
}