package com.example.ecoride26611_30359.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument

// Importação das Screens
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

// Definição das Rotas
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
    // Estado global que guarda o ID do utilizador logado
    var loggedUserId by remember { mutableIntStateOf(-1) }

    NavHost(
        navController = navController,
        startDestination = AppRoutes.Login.route
    ) {
        // LOGIN
        composable(AppRoutes.Login.route) {
            LoginScreen(
                navController = navController,
                onLoginSuccess = { userId ->
                    loggedUserId = userId
                }
            )
        }

        // SIGN IN
        composable(AppRoutes.SignIn.route) { SignInScreen(navController) }

        // HOME
        composable(AppRoutes.Home.route) { HomeScreen(navController) }

        // DASHBOARD CONDUTOR
        composable(AppRoutes.DashboardDriver.route) {
            DashboardDriverScreen(navController, loggedUserId)
        }

        // DASHBOARD PASSAGEIRO
        composable(AppRoutes.DashboardPassenger.route) {
            DashboardPassengerScreen(navController, loggedUserId)
        }

        // CHECKOUT CONDUTOR
        composable(AppRoutes.CheckoutDriver.route) { CheckoutDriverScreen(navController) }

        // CHECKOUT PASSAGEIRO
        composable(
            route = AppRoutes.CheckoutPassenger.route + "/{tripId}",
            arguments = listOf(navArgument("tripId") { type = NavType.IntType })
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getInt("tripId") ?: -1
            CheckoutPassengerScreen(
                navController = navController,
                loggedUserId = loggedUserId,
                tripId = tripId
            )
        }

        // PAGAMENTO
        composable(
            route = AppRoutes.Payment.route + "?from={from}&tripId={tripId}",
            arguments = listOf(
                navArgument("from") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = "home"
                },
                navArgument("tripId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val from = backStackEntry.arguments?.getString("from")
            val tripId = backStackEntry.arguments?.getInt("tripId") ?: -1
            PaymentScreen(
                navController = navController,
                from = from,
                tripId = tripId,
                userId = loggedUserId
            )
        }

        // LISTA DE CONVERSAS
        composable(AppRoutes.Chat.route) {
            ChatScreen(navController, loggedUserId)
        }

        // ECRÃ DE MENSAGENS
        composable(
            route = AppRoutes.Messages.route + "/{tripId}",
            arguments = listOf(navArgument("tripId") { type = NavType.IntType })
        ) {
            MessagesScreen(
                navController = navController,
                loggedUserId = loggedUserId
            )
        }

        // PERFIL
        composable(AppRoutes.Profile.route) {
            ProfileScreen(navController, loggedUserId)
        }

        // CONQUISTAS
        composable(AppRoutes.Achievements.route) {
            AchievementsScreen()
        }
    }
}