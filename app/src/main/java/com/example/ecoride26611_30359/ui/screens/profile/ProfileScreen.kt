package com.example.ecoride26611_30359.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ecoride26611_30359.R
import com.example.ecoride26611_30359.navigation.AppRoutes

@Composable
fun ProfileScreen(navController: NavHostController) {

    val userName = "Alfredo Martins"
    val userEmail = "alfredo.martins@email.com"
    val userRating = 4.7

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Perfil",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(25.dp))

        Image(
            painter = painterResource(id = R.drawable.boy),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .background(Color.LightGray, CircleShape)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // --- BOX DE INFORMAÇÕES PESSOAIS ---
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.elevatedCardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Text(
                    text = "Informações Pessoais",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Divider(modifier = Modifier.padding(vertical = 10.dp))

                Text(text = "Nome:", fontWeight = FontWeight.SemiBold)
                Text(text = userName, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "Email:", fontWeight = FontWeight.SemiBold)
                Text(text = userEmail, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "Classificação:", fontWeight = FontWeight.SemiBold)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFC107)
                    )
                    Text(
                        text = "$userRating estrelas",
                        color = Color.DarkGray,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botão Logout
        Button(
            onClick = { navController.navigate(AppRoutes.Login.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("LOG OUT", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(15.dp))
    }
}
