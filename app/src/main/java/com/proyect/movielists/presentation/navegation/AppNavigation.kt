package com.proyect.movielists.presentation.navegation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.proyect.movielists.presentation.components.scaffold.MainScreen
import com.proyect.movielists.presentation.screens.login.LoginScreen

@Composable
fun AppNavigation(navController: NavHostController){
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    NavHost(
        navController = navController,
        startDestination = "login"
    ){
        composable("login") {
            LoginScreen(navController, snackBarHostState, coroutineScope)
        }
        composable("main") {
            MainScreen()
        }
    }
}