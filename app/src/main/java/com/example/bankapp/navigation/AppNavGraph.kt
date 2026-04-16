package com.example.bankapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bankapp.ui.screen.HistoryScreen
import com.example.bankapp.ui.screen.HomeScreen
import com.example.bankapp.ui.screen.LoginScreen
import com.example.bankapp.ui.screen.TransferScreen
import com.example.bankapp.viewmodel.HomeViewModel
import com.example.bankapp.viewmodel.LoginViewModel
import com.example.bankapp.viewmodel.TransferViewModel

// Daftar Route
object Routes {
    const val LOGIN = "login"
    const val HOME = "home"
    const val TRANSFER = "transfer"
    const val HISTORY = "history"
}

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    val loginViewModel: LoginViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()
    val transferViewModel: TransferViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                viewModel = homeViewModel,
                onNavigateToTransfer = {
                    navController.navigate(Routes.TRANSFER)
                },
                onNavigateToHistory = {
                    navController.navigate(Routes.HISTORY)
                }
            )
        }

        composable(Routes.TRANSFER) {
            TransferScreen(
                viewModel = transferViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.HISTORY) {
            HistoryScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}