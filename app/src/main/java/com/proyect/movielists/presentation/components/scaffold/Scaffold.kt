package com.proyect.movielists.presentation.components.scaffold


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.mytemplate.presentation.navegation.Screen
import com.proyect.movielists.presentation.components.drawer.DrawerProfile
import com.proyect.movielists.presentation.screens.dashboard.DashboardScreen
import com.proyect.movielists.presentation.screens.lists.ListsScreen
import com.proyect.movielists.presentation.screens.Favorites.FavoritesScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun MainScreen(
    navControllerAppNavigation: NavHostController,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    val navControllerMainScreen = rememberNavController()
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
                        navControllerAppNavigation = navControllerAppNavigation,
                        snackBarHostState = snackBarHostState,
                        coroutineScope = coroutineScope,
                        drawerState = drawerState
                    )
                },
                bottomBar = {
                    BottomNavigationBar(navControllerMainScreen)
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackBarHostState)
                },
                content = { innerPadding ->
                    NavHost(
                        navController = navControllerMainScreen,
                        startDestination = Screen.Dashboard.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Lists.route) {
                            ListsScreen(
                                navControllerAppNavigation = navControllerAppNavigation,
                                snackBarHostState = snackBarHostState,
                                coroutineScope = coroutineScope
                            )
                        }
                        composable(Screen.Dashboard.route) {
                            DashboardScreen(
                                navControllerAppNavigation = navControllerAppNavigation,
                                snackBarHostState = snackBarHostState,
                                coroutineScope = coroutineScope,
                            )
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
