package com.proyect.movielists.presentation.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.proyect.movielists.presentation.components.shared.Loading
import com.proyect.movielists.presentation.screens.dashboard.component.FavoriteMoviesCarousel
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(
    navControllerAppNavigation: NavHostController,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
) {
    val viewModel: DashboardViewModel = koinViewModel()
    val favorites by viewModel.favorites.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val expandMenu = remember { mutableStateOf(false) }
    val movieID = remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState)  {
            is UIState.Loading -> {
                Loading()
            }
            is UIState.Error -> {
                Text(text = (uiState as UIState.Error).message)
            }
            is UIState.Success -> {
                Column (
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                    FavoriteMoviesCarousel(
                        favoriteMovies = favorites,
                        onTapMovie = { movieId ->
                            navControllerAppNavigation.navigate("movie/$movieId")
                        },
                        onLongPressMovie = {
                            movieID.value = it
                            expandMenu.value = true
                        }
                    )
                }
            }
            is UIState.Idle -> {}
        }
    }
}












