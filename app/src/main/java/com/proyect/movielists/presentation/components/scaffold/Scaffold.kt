package com.proyect.movielists.presentation.components.scaffold


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.mytemplate.presentation.navegation.Screen
import com.proyect.movielists.presentation.components.drawer.DrawerProfile
import com.proyect.movielists.presentation.screens.watched.WatchedScreen
import com.proyect.movielists.presentation.screens.lists.ListsScreen
import com.proyect.movielists.presentation.screens.explorer.ExplorerScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun MainScreen(
    navControllerAppNavigation: NavHostController,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    val navControllerMainScreen = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var searchActive by remember { mutableStateOf(false) }
    val gesturesEnabled = rememberSaveable { mutableStateOf(false) }

    if (drawerState.isOpen) {
        LaunchedEffect(Unit) {
            gesturesEnabled.value = true
        }
    } else {
        LaunchedEffect(Unit) {
            gesturesEnabled.value = false
        }
    }

    ModalNavigationDrawer(
        gesturesEnabled = gesturesEnabled.value,
        drawerState = drawerState,
        drawerContent = {
            DrawerProfile(
                navControllerAppNavigation = navControllerAppNavigation,
                snackBarHostState = snackBarHostState,
                coroutineScope = coroutineScope,
                setGesturesEnabled = { gesturesEnabled.value = it }
            )
        },
        content = {
            Scaffold(
                topBar = {
                    TopBar(
                        navControllerAppNavigation = navControllerAppNavigation,
                        snackBarHostState = snackBarHostState,
                        coroutineScope = coroutineScope,
                        drawerState = drawerState,
                        searchActive = searchActive,
                        onSearchActiveChange = { searchActive = it }
                    )
                },
                bottomBar = {
                    BottomNavigationBar(
                        navController = navControllerMainScreen,
                        onBottomNavItemClick = { searchActive = false }
                    )
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackBarHostState)
                },
                content = { innerPadding ->
                    NavHost(
                        navController = navControllerMainScreen,
                        startDestination = Screen.Explorer.route,
                        Modifier
                            .padding(innerPadding)
                            .clickable {
                                gesturesEnabled.value = false
                            }
                    ) {
                        composable(Screen.Lists.route) {
                            ListsScreen(
                                navControllerAppNavigation = navControllerAppNavigation,
                                snackBarHostState = snackBarHostState,
                                coroutineScope = coroutineScope
                            )
                        }
                        composable(Screen.Watched.route) {
                            WatchedScreen(
                                navControllerAppNavigation = navControllerAppNavigation,
                                snackBarHostState = snackBarHostState,
                                coroutineScope = coroutineScope,
                            )
                        }
                        composable(Screen.Explorer.route) {
                            ExplorerScreen(
                                navControllerAppNavigation = navControllerAppNavigation,
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