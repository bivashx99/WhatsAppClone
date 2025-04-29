package com.bivashmallick.whatsappclone.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bivashmallick.whatsappclone.presentation.screens.ChatDetailScreen
import com.bivashmallick.whatsappclone.presentation.screens.MainScreen

sealed class Screen(val route: String) {
    data object Main : Screen("main")
    data object ChatDetail : Screen("chat/{chatId}") {
        fun createRoute(chatId: String) = "chat/$chatId"
    }
}

@Composable
fun WhatsAppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.ChatDetail.route
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            ChatDetailScreen(
                chatId = chatId,
                navController = navController
            )
        }
    }
}

fun NavController.navigateToChatDetail(chatId: String) {
    this.navigate(Screen.ChatDetail.createRoute(chatId))
} 