package com.example.ecoride26611_30359

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ecoride26611_30359.navigation.AppNavigation
import com.example.ecoride26611_30359.navigation.AppRoutes
import com.example.ecoride26611_30359.ui.EcoRide26611_30359Theme
import com.example.ecoride26611_30359.ui.components.BottomNavBar



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcoRide26611_30359Theme {

                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        val showBottomBarRoutes = listOf(
                            AppRoutes.Home.route,
                            AppRoutes.DashboardDriver.route,
                            AppRoutes.DashboardPassenger.route,
                            AppRoutes.Chat.route,
                            AppRoutes.Profile.route,
                            AppRoutes.Achievements.route


                        )
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route
                        if (showBottomBarRoutes.contains(currentRoute)) {
                            BottomNavBar(navController)
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation(navController)
                    }
                }
            }
        }

    }
}
