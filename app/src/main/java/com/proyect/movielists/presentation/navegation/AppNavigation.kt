package com.proyect.movielists.presentation.navegation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.proyect.movielists.presentation.components.scaffold.MainScreen
import com.proyect.movielists.presentation.screens.login.LoginScreen
import com.proyect.movielists.presentation.screens.list.ListScreen
import com.proyect.movielists.presentation.screens.movie.MovieScreen

@Composable
fun AppNavigation(){
    val navControllerAppNavigation = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    NavHost(
        navController = navControllerAppNavigation,
        startDestination = "login"
    ){
        composable("login") {
            LoginScreen(navControllerAppNavigation, snackBarHostState, coroutineScope)
        }
        composable("main") {
            MainScreen(navControllerAppNavigation, snackBarHostState, coroutineScope)
        }
        composable("movie/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
            MovieScreen( movieId = movieId!!) {
                navControllerAppNavigation.popBackStack()
            }
        }
        composable("list/{listId}") { backStackEntry ->
            val listId = backStackEntry.arguments?.getString("listId")
            ListScreen(listId = listId!!)
        }

    }
}