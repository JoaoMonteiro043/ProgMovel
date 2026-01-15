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
import com.example.ecoride26611_30359.ui.screens.driver.MapPickerScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecoride26611_30359.ui.screens.driver.DashboardDriverViewModel

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
    var loggedUserId by remember { mutableIntStateOf(-1) }

    NavHost(navController = navController, startDestination = AppRoutes.Login.route) {
        composable(AppRoutes.Login.route) {
            LoginScreen(navController = navController, onLoginSuccess = { loggedUserId = it })
        }
        composable(AppRoutes.SignIn.route) { SignInScreen(navController) }
        composable(AppRoutes.Home.route) { HomeScreen(navController, loggedUserId) }
        composable(AppRoutes.DashboardDriver.route) {
            DashboardDriverScreen(
                navController,
                loggedUserId
            )
        }
        composable(AppRoutes.DashboardPassenger.route) {
            DashboardPassengerScreen(
                navController,
                loggedUserId
            )
        }

        // Checkout Driver
        composable(
            route = AppRoutes.CheckoutDriver.route + "?origemId={origemId}&destinoId={destinoId}&data={data}&lugares={lugares}",
            arguments = listOf(
                navArgument("origemId") { type = NavType.IntType },
                navArgument("destinoId") { type = NavType.IntType },
                navArgument("data") { type = NavType.StringType },
                navArgument("lugares") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            CheckoutDriverScreen(
                navController = navController,
                origemId = backStackEntry.arguments?.getInt("origemId") ?: 0,
                destinoId = backStackEntry.arguments?.getInt("destinoId") ?: 0,
                data = backStackEntry.arguments?.getString("data") ?: "",
                lugares = backStackEntry.arguments?.getInt("lugares") ?: 1
            )
        }

        composable(
            route = AppRoutes.CheckoutPassenger.route + "/{tripId}",
            arguments = listOf(navArgument("tripId") { type = NavType.IntType })
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getInt("tripId") ?: -1
            CheckoutPassengerScreen(navController, loggedUserId, tripId)
        }

        // Payment
        composable(
            route = AppRoutes.Payment.route +
                    "?from={from}&tripId={tripId}" +
                    "&origemId={origemId}&destinoId={destinoId}" +
                    "&origemLabel={origemLabel}&destinoLabel={destinoLabel}" +
                    "&data={data}&lugares={lugares}",
            arguments = listOf(
                navArgument("from") { type = NavType.StringType },
                navArgument("tripId") { type = NavType.IntType; defaultValue = -1 },

                navArgument("origemId") { type = NavType.IntType; defaultValue = -1 },
                navArgument("destinoId") { type = NavType.IntType; defaultValue = -1 },

                navArgument("origemLabel") { type = NavType.StringType; nullable = true },
                navArgument("destinoLabel") { type = NavType.StringType; nullable = true },

                navArgument("data") { type = NavType.StringType; nullable = true },
                navArgument("lugares") { type = NavType.IntType; defaultValue = 1 }
            )
        ) { backStackEntry ->
            PaymentScreen(
                navController = navController,
                from = backStackEntry.arguments?.getString("from"),
                tripId = backStackEntry.arguments?.getInt("tripId") ?: -1,
                userId = loggedUserId,

                origemCheckpointId = backStackEntry.arguments?.getInt("origemId") ?: -1,
                destinoCheckpointId = backStackEntry.arguments?.getInt("destinoId") ?: -1,

                origemLabel = backStackEntry.arguments?.getString("origemLabel"),
                destinoLabel = backStackEntry.arguments?.getString("destinoLabel"),
                data = backStackEntry.arguments?.getString("data"),
                lugares = backStackEntry.arguments?.getInt("lugares") ?: 1
            )
        }

        composable(AppRoutes.Chat.route) { ChatScreen(navController, loggedUserId) }
        composable(
            route = AppRoutes.Messages.route + "/{tripId}",
            arguments = listOf(navArgument("tripId") { type = NavType.IntType })
        ) { MessagesScreen(navController, loggedUserId) }
        composable(AppRoutes.Profile.route) { ProfileScreen(navController, loggedUserId) }
        composable(AppRoutes.Achievements.route) { AchievementsScreen() }

        // MOVED INSIDE THE NAVHOST BLOCK:
        composable(
            route = "map/{mode}",
            arguments = listOf(navArgument("mode") { type = NavType.StringType })
        ) { backStack ->

            val parentEntry = remember(backStack) {
                navController.getBackStackEntry(AppRoutes.DashboardDriver.route)
            }

            val driverViewModel: DashboardDriverViewModel = viewModel(parentEntry)

            val mode = backStack.arguments?.getString("mode")!!

            MapPickerScreen(
                navController = navController,
                mode = mode
            ) { checkpointId, checkpointName ->

                if (mode == "origem") {
                    driverViewModel.updateOrigem(checkpointId, checkpointName)
                } else {
                    driverViewModel.updateDestino(checkpointId, checkpointName)
                }
            }
        } // This brace now correctly closes the NavHost
    }
}