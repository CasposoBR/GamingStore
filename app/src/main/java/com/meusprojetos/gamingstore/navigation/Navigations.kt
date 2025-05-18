package com.meusprojetos.gamingstore.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.meusprojetos.gamingstore.remote.ApiService
import com.meusprojetos.gamingstore.screens.HomeScreen
import com.meusprojetos.gamingstore.screens.LoginScreen
import com.meusprojetos.gamingstore.screens.RegisterScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    var screenState by remember { mutableStateOf("login") }

    LaunchedEffect(Unit) {
        screenState = ApiService.getSessionState("123")  // 🔹 Ktor decide a navegação
    }

    NavHost(navController, startDestination = screenState) {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("home") { HomeScreen(navController) }
    }
}