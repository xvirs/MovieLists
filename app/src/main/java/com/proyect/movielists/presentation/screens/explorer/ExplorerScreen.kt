package com.proyect.movielists.presentation.screens.explorer


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.proyect.movielists.presentation.components.shared.Loading
import com.proyect.movielists.presentation.models.ListItemUI
import com.proyect.movielists.presentation.screens.explorer.component.MovieCategorySection
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExplorerScreen(
    navControllerAppNavigation: NavHostController,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
) {
    val viewModel: ExplorerViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val popularMoviesState by viewModel.popularMovies.collectAsState()
    val upcomingMoviesState by viewModel.upcomingMovies.collectAsState()
    val nowPlayingMoviesState by viewModel.nowPlayingMovies.collectAsState()
    val topRatedMoviesState by viewModel.topRatedMovies.collectAsState()
    val isFavoriteState by viewModel.isFavorite.collectAsState()
    val expandMenu = remember { mutableStateOf(false) }
    val moviesLists by viewModel.moviesLists.collectAsState()
    val movieID = remember { mutableStateOf(0) }

    val popularExpandMenuIndex = remember { mutableStateOf(-1) }
    val upcomingExpandMenuIndex = remember { mutableStateOf(-1) }
    val nowPlayingExpandMenuIndex = remember { mutableStateOf(-1) }
    val topRatedExpandMenuIndex = remember { mutableStateOf(-1) }

    fun closeOtherMenus( otherMenus: List<MutableState<Int>>) {
        otherMenus.forEach { it.value = -1 }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            when (uiState) {
                is UIState.Loading -> {
                    Loading()
                }
                is UIState.Success -> {
                    AddMovieToLists(
                        expandMenu = expandMenu,
                        movieList = moviesLists,
                        onAddToListClick = {
                            viewModel.addMovieToList(it.toString(), movieID.value)
                            expandMenu.value = false
                        }
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues()
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        item {
                            MovieCategorySection(
                                title = "Popular Movies",
                                icon = Icons.Default.Star,
                                iconColor = Color.Magenta,
                                movies = popularMoviesState,
                                onTapMovie = { movieId ->
                                    navControllerAppNavigation.navigate("movie/$movieId")
                                },
                                onLongPressMovie = {
                                    movieID.value = it
                                    closeOtherMenus(listOf(upcomingExpandMenuIndex, nowPlayingExpandMenuIndex, topRatedExpandMenuIndex))
                                    viewModel.isFavorite(it)
                                },
                                expandMenuIndex = popularExpandMenuIndex,
                                onAddToList = {
                                    expandMenu.value = true
                                },
                                onShare = {

                                },
                                isFavorite = isFavoriteState,
                                addFavorite = { viewModel.addFavorite(it) },
                                removeFavorite = { viewModel.addFavorite(it) }

                            )
                        }

                        item {
                            MovieCategorySection(
                                title = "Upcoming Movies",
                                icon = Icons.Default.DateRange,
                                iconColor = Color.Green,
                                movies = upcomingMoviesState,
                                onTapMovie = { movieId ->
                                    navControllerAppNavigation.navigate("movie/$movieId")
                                },
                                onLongPressMovie = {
                                    movieID.value = it
                                    closeOtherMenus(listOf(popularExpandMenuIndex, nowPlayingExpandMenuIndex, topRatedExpandMenuIndex))
                                    viewModel.isFavorite(movieID.value)
                                },
                                expandMenuIndex = upcomingExpandMenuIndex,
                                onAddToList = {
                                    expandMenu.value = true
                                },
                                onShare = {

                                },
                                isFavorite = isFavoriteState,
                                addFavorite = { viewModel.addFavorite(it) },
                                removeFavorite = { viewModel.addFavorite(it) }

                            )
                        }

                        item {
                            MovieCategorySection(
                                title = "Now Playing Movies",
                                icon = Icons.Default.Movie,
                                iconColor = Color.Red,
                                movies = nowPlayingMoviesState,
                                onTapMovie = { movieId ->
                                    navControllerAppNavigation.navigate("movie/$movieId")
                                },
                                onLongPressMovie = {
                                    movieID.value = it
                                    closeOtherMenus(listOf(popularExpandMenuIndex, upcomingExpandMenuIndex, topRatedExpandMenuIndex))
                                    viewModel.isFavorite(it)
                                },
                                expandMenuIndex = nowPlayingExpandMenuIndex,
                                onAddToList = {
                                    expandMenu.value = true
                                },
                                onShare = {

                                },
                                isFavorite = isFavoriteState,
                                addFavorite = { viewModel.addFavorite(it) },
                                removeFavorite = { viewModel.addFavorite(it) }

                            )
                        }

                        item {
                            MovieCategorySection(
                                title = "Top Rated Movies",
                                icon = Icons.Default.ThumbUp,
                                iconColor = Color.Blue,
                                movies = topRatedMoviesState,
                                onTapMovie = { movieId ->
                                    navControllerAppNavigation.navigate("movie/$movieId")
                                },
                                onLongPressMovie = {
                                    movieID.value = it
                                    closeOtherMenus(listOf(popularExpandMenuIndex, upcomingExpandMenuIndex, nowPlayingExpandMenuIndex))
                                    viewModel.isFavorite(it)
                                },
                                expandMenuIndex = topRatedExpandMenuIndex,
                                onAddToList = {
                                    expandMenu.value = true
                                },
                                onShare = {

                                },
                                isFavorite = isFavoriteState,
                                addFavorite = { viewModel.addFavorite(it) },
                                removeFavorite = { viewModel.addFavorite(it) }

                            )
                        }
                    }

                }
                is UIState.Error -> {
                    Text(
                        text = (uiState as UIState.Error).message,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else -> {
                }
            }
        }

    }
}


@Composable
fun AddMovieToLists(
    expandMenu: MutableState<Boolean>,
    movieList: List<ListItemUI>,
    onAddToListClick: (Int) -> Unit
) {
    DropdownMenu(
        expanded = expandMenu.value,
        onDismissRequest = { expandMenu.value = false }
    ) {
        movieList.forEach { list ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = list.name,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onSurface,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                onClick = {
                    onAddToListClick(list.id)
                    expandMenu.value = false
                }
            )
        }
    }
}
