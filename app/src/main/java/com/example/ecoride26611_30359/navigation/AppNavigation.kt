package com.example.ecoride26611_30359.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument

import com.example.ecoride26611_30359.ui.screens.login.LoginScreen
import com.example.ecoride26611_30359.ui.screens.signin.SignInScreen
import com.example.ecoride26611_30359.ui.screens.home.HomeScreen
import com.example.ecoride26611_30359.ui.screens.driver.DashboardDriverScreen
import com.example.ecoride26611_30359.ui.screens.passenger.DashboardPassengerScreen
import com.example.ecoride26611_30359.ui.screens.passenger.CheckoutPassengerScreen
import com.example.ecoride26611_30359.ui.screens.driver.CheckoutDriverScreen
import com.example.ecoride26611_30359.ui.screens.payment.PaymentScreen
import com.example.ecoride26611_30359.ui.screens.chat.ChatScreen
import com.example.ecoride26611_30359.ui.screens.chat.MessagesScreen
import com.example.ecoride26611_30359.ui.screens.profile.ProfileScreen
import com.example.ecoride26611_30359.ui.screens.achievements.AchievementsScreen

sealed class AppRoutes(val route: String) {
    object Login : AppRoutes("login")
    object SignIn : AppRoutes("signin")
    object Home : AppRoutes("home")
    object DashboardDriver : AppRoutes("dashboard_driver")
    object DashboardPassenger : AppRoutes("dashboard_passenger")
    object CheckoutDriver : AppRoutes("checkout_driver")
    object CheckoutPassenger : AppRoutes("checkout_passenger")
    object Payment : AppRoutes("payment")
    object Chat : AppRoutes("chat")
    object Messages : AppRoutes("messages")
    object Profile : AppRoutes("profile")
    object Achievements : AppRoutes("achievements")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    var loggedUserId by remember { mutableStateOf(-1) }

    NavHost(
        navController = navController,
        startDestination = AppRoutes.Login.route
    ) {
        composable(AppRoutes.Login.route) {
            LoginScreen(
                navController = navController,
                onLoginSuccess = { userId ->
                    loggedUserId = userId
                }
            )
        }

        composable(AppRoutes.SignIn.route) { SignInScreen(navController) }
        composable(AppRoutes.Home.route) { HomeScreen(navController) }

        composable(AppRoutes.DashboardDriver.route) {
            DashboardDriverScreen(navController, loggedUserId)
        }

        composable(AppRoutes.DashboardPassenger.route) { DashboardPassengerScreen(navController) }
        composable(AppRoutes.CheckoutDriver.route) { CheckoutDriverScreen(navController) }

        composable(
            route = AppRoutes.CheckoutPassenger.route + "/{tripId}",
            arguments = listOf(navArgument("tripId") { type = NavType.IntType })
        ) {
            CheckoutPassengerScreen(navController)
        }

        composable(
            route = AppRoutes.Payment.route + "?from={from}",
            arguments = listOf(navArgument("from") {
                type = NavType.StringType
                nullable = true
                defaultValue = "home"
            })
        ) { backStackEntry ->
            val from = backStackEntry.arguments?.getString("from")
            PaymentScreen(navController, from)
        }

        composable(AppRoutes.Chat.route) { ChatScreen(navController) }
        composable(AppRoutes.Messages.route) { MessagesScreen(navController = navController) }

        composable(AppRoutes.Profile.route) {
            ProfileScreen(navController, loggedUserId)
        }

        composable(AppRoutes.Achievements.route) { AchievementsScreen() }
    }
}
