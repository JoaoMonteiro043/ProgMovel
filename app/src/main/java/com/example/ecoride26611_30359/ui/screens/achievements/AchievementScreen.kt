package com.example.ecoride26611_30359.ui.screens.achievements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MilitaryTech
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecoride26611_30359.ui.EcoRide26611_30359Theme

// 1. Modelo de dados simples
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

// 2. O ecrã principal de Conquistas (Sem ViewModel, com dados estáticos)
@Composable
fun AchievementsScreen() {
    // Lista de conquistas estática definida localmente
    val achievements = listOf(
        Achievement(
            id = 1,
            title = "Primeira Viagem",
            description = "Completou a sua primeira boleia com sucesso.",
            icon = Icons.Default.Star,
            currentProgress = 1,
            targetProgress = 1
        ),
        Achievement(
            id = 2,
            title = "Eco-Amigo",
            description = "Poupe 50kg de CO2 partilhando viagens.",
            icon = Icons.Default.VerifiedUser,
            currentProgress = 20,
            targetProgress = 50
        ),
        Achievement(
            id = 3,
            title = "Condutor de Elite",
            description = "Realize 10 viagens como condutor.",
            icon = Icons.Default.MilitaryTech,
            currentProgress = 3,
            targetProgress = 10
        ),
        Achievement(
            id = 4,
            title = "Passageiro Frequente",
            description = "Reserve 5 boleias na aplicação.",
            icon = Icons.Default.WorkspacePremium,
            currentProgress = 5,
            targetProgress = 5
        )
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "As Suas Conquistas",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(achievements) { achievement ->
                AchievementItem(achievement = achievement)
            }
        }
    }
}

// 3. O Composable para um item individual
@Composable
fun AchievementItem(achievement: Achievement) {
    val iconColor = if (achievement.isUnlocked) MaterialTheme.colorScheme.primary else Color.Gray
    val backgroundColor = if (achievement.isUnlocked)
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
    else
        MaterialTheme.colorScheme.surfaceVariant

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                    Text(
                        text = "${achievement.currentProgress} / ${achievement.targetProgress}",
                        fontSize = 10.sp,
                        modifier = Modifier.align(Alignment.End).padding(top = 2.dp)
                    )
                } else {
                    Text(
                        text = "Concluído!",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

// 4. Previews
@Preview(showBackground = true)
@Composable
fun AchievementsScreenPreview() {
    EcoRide26611_30359Theme {
        AchievementsScreen()
    }
}