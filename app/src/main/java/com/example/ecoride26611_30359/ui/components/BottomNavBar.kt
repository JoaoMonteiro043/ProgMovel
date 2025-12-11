package com.example.ecoride26611_30359.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ecoride26611_30359.navigation.AppRoutes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Person

@Composable
fun BottomNavBar(navController: NavHostController) {

    NavigationBar(containerColor = Color.White) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val items = listOf(
            AppRoutes.Home,
            AppRoutes.Chat,
            AppRoutes.Profile,
            AppRoutes.Achievements,
        )

        items.forEach { route ->
            NavigationBarItem(
                selected = currentRoute == route.route,
                onClick = { navController.navigate(route.route) },
                icon = {
                    when (route) {
                        AppRoutes.Home -> Icon(Icons.Default.Home, contentDescription = "Home")
                        AppRoutes.Chat -> Icon(Icons.Default.Email, contentDescription = "Chat")
                        AppRoutes.Profile -> Icon(Icons.Default.Person, contentDescription = "Profile")
                        AppRoutes.Achievements -> Icon(Icons.Default.EmojiEvents, contentDescription = "Achievements")
                        else -> {}
                    }
                },
                alwaysShowLabel = false
            )
        }
    }
}
