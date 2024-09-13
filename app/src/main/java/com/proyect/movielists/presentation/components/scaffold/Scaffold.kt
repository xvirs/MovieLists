package com.proyect.movielists.presentation.components.scaffold


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.mytemplate.presentation.navegation.Screen
import com.proyect.movielists.presentation.components.drawer.DrawerProfile
import com.proyect.movielists.presentation.screens.movies.MoviesScreen
import com.proyect.movielists.presentation.screens.Lists.ListsScreen
import com.proyect.movielists.presentation.screens.Favorites.FavoritesScreen

@Preview
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerProfile()
        },
        content = {
            Scaffold(
                topBar = {
                    TopBar(
                        snackBarHostState = snackBarHostState,
                        coroutineScope = coroutineScope,
                        drawerState = drawerState
                    )
                },
                bottomBar = {
                    BottomNavigationBar(navController)
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackBarHostState)
                },
                content = { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = Screen.Movies.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Profile.route) {
                            ListsScreen(
                                snackBarHostState = snackBarHostState,
                                coroutineScope = coroutineScope
                            )
                        }
                        composable(Screen.Movies.route) {
                            MoviesScreen()
                        }
                        composable(Screen.Screen3.route) {
                            FavoritesScreen(
                                snackBarHostState = snackBarHostState,
                                coroutineScope = coroutineScope
                            )
                        }
                    }
                }
            )
        }
    )
}





