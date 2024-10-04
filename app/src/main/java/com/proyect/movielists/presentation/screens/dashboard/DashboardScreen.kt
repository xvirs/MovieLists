package com.proyect.movielists.presentation.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
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
import com.proyect.movielists.presentation.models.MovieUI
import com.proyect.movielists.presentation.screens.dashboard.component.FilterChipLazyRow
import com.proyect.movielists.presentation.screens.dashboard.component.MovieListsDropdownMenu
import com.proyect.movielists.presentation.screens.dashboard.component.MoviesContent
import com.proyect.movielists.utils.MovieListType
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(
    navControllerAppNavigation: NavHostController,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    viewModel: DashboardViewModel = koinViewModel()
) {
    val moviesState by viewModel.moviesState.collectAsState()
    val movieListsState by viewModel.movieListsState.collectAsState()
    val selectedMovieListType = remember { mutableStateOf(MovieListType.POPULAR) }
    val expandMenu = remember { mutableStateOf(false) }
    val movieID = remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            FilterChipLazyRow(
                selectedMovieListType = selectedMovieListType.value,
                onChipSelected = { movieListType ->
                    selectedMovieListType.value = movieListType
                    viewModel.fetchMovies(movieListType)
                }
            )

            when (moviesState) {
                is UIState.Loading -> {
                    Loading()
                }
                is UIState.Success -> {
                    MoviesContent(
                        movies = (moviesState as UIState.Success<List<MovieUI>>).data,
                        onTapMovie = { movieId ->
                            navControllerAppNavigation.navigate("movie/$movieId")
                            coroutineScope.launch {
                                snackBarHostState.showSnackbar(
                                    message = movieId.toString(),
                                    withDismissAction = true
                                )
                            }
                        },
                        onLongPressMovie = {
                            movieID.value = it
                            expandMenu.value = true
                        }
                    )
                }
                is UIState.Error -> {
                    Text(
                        text = (moviesState as UIState.Error).message,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else -> {}
            }

            MovieListsDropdownMenu(
                movieListsState = movieListsState,
                expandMenu = expandMenu.value,
                onDismissMenu = { expandMenu.value = false },
                onMovieListSelected = { listId ->
                    viewModel.addMovieToList(listId, movieID.value)
                    expandMenu.value = false
                }
            )
        }
    }
}












