package com.proyect.movielists.presentation.screens.explorer


import android.content.Context
import android.content.Intent
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.proyect.movielists.presentation.components.shared.AddMovieToListDialog
import com.proyect.movielists.presentation.components.shared.Loading
import com.proyect.movielists.presentation.models.MovieDetailsUI
import com.proyect.movielists.presentation.screens.explorer.component.MovieCategorySection
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
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
    val movieId = remember { mutableStateOf(0) }
    val context = LocalContext.current

    val popularExpandMenuIndex = remember { mutableStateOf(-1) }
    val upcomingExpandMenuIndex = remember { mutableStateOf(-1) }
    val nowPlayingExpandMenuIndex = remember { mutableStateOf(-1) }
    val topRatedExpandMenuIndex = remember { mutableStateOf(-1) }

    fun closeOtherMenus( otherMenus: List<MutableState<Int>>) {
        otherMenus.forEach { it.value = -1 }
    }

    val showDialog = remember { mutableStateOf(false) }

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
                    if (expandMenu.value) {
                        AddMovieToListDialog(
                            movieId = movieId.value,
                            movieList = moviesLists,
                            onAddToListClick = { movieId, listId ->
                                coroutineScope.launch {
                                    snackBarHostState.showSnackbar(
                                        message = "Pelicula Guardada! :)",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                                viewModel.addMovieToList(listId.toString(), movieId)
                            },
                            onCreateNewListClick = { title, description, movieId ->
                                coroutineScope.launch {
                                    snackBarHostState.showSnackbar(
                                        message = "Pelicula Guardada en nueva lista! :)",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                                viewModel.createMovieList(title, description, "es", movieId)
                            },
                            onDismiss = { expandMenu.value = false }
                        )
                    }
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
                                title = "Populares",
                                icon = Icons.Default.Star,
                                iconColor = Color.Magenta,
                                movies = popularMoviesState,
                                onTapMovie = { movieId ->
                                    navControllerAppNavigation.navigate("movie/$movieId")
                                },
                                onLongPressMovie = {
                                    movieId.value = it
                                    closeOtherMenus(listOf(upcomingExpandMenuIndex, nowPlayingExpandMenuIndex, topRatedExpandMenuIndex))
                                    viewModel.isFavorite(it)
                                },
                                expandMenuIndex = popularExpandMenuIndex,
                                onAddToList = {
                                    expandMenu.value = true
                                },
                                onShare = {
                                    coroutineScope.launch {
                                        viewModel.getMovie(it.toString())
                                            ?.let { it1 -> context.shareMovie(it1) }
                                    }
                                },
                                isWatched = isFavoriteState,
                                addWatched = {
                                    viewModel.addFavorite(it)
                                    coroutineScope.launch {
                                        snackBarHostState.showSnackbar(
                                            message = "Pelicula marcada como Vista",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                },
                                removeWatched = {
                                    viewModel.removeFavorite(it)
                                    coroutineScope.launch {
                                        snackBarHostState.showSnackbar(
                                            message = "Pelicula desmarcada como Vista",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                            )
                        }

                        item {
                            MovieCategorySection(
                                title = "Proximas",
                                icon = Icons.Default.DateRange,
                                iconColor = Color.Green,
                                movies = upcomingMoviesState,
                                onTapMovie = { movieId ->
                                    navControllerAppNavigation.navigate("movie/$movieId")
                                },
                                onLongPressMovie = {
                                    movieId.value = it
                                    closeOtherMenus(listOf(popularExpandMenuIndex, nowPlayingExpandMenuIndex, topRatedExpandMenuIndex))
                                    viewModel.isFavorite(movieId.value)
                                },
                                expandMenuIndex = upcomingExpandMenuIndex,
                                onAddToList = {
                                    expandMenu.value = true
                                },
                                onShare = {
                                    coroutineScope.launch {
                                        viewModel.getMovie(it.toString())
                                            ?.let { it1 -> context.shareMovie(it1) }
                                    }
                                },
                                isWatched = isFavoriteState,
                                addWatched = {
                                    viewModel.addFavorite(it)
                                    coroutineScope.launch {
                                        snackBarHostState.showSnackbar(
                                            message = "Pelicula marcada como Vista",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                },
                                removeWatched = {
                                    viewModel.removeFavorite(it)
                                    coroutineScope.launch {
                                        snackBarHostState.showSnackbar(
                                            message = "Pelicula desmarcada como Vista",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                            )
                        }

                        item {
                            MovieCategorySection(
                                title = "Estrenos",
                                icon = Icons.Default.Movie,
                                iconColor = Color.Red,
                                movies = nowPlayingMoviesState,
                                onTapMovie = { movieId ->
                                    navControllerAppNavigation.navigate("movie/$movieId")
                                },
                                onLongPressMovie = {
                                    movieId.value = it
                                    closeOtherMenus(listOf(popularExpandMenuIndex, upcomingExpandMenuIndex, topRatedExpandMenuIndex))
                                    viewModel.isFavorite(it)
                                },
                                expandMenuIndex = nowPlayingExpandMenuIndex,
                                onAddToList = {
                                    expandMenu.value = true
                                },
                                onShare = {
                                    coroutineScope.launch {
                                        viewModel.getMovie(it.toString())
                                            ?.let { it1 -> context.shareMovie(it1) }
                                    }
                                },
                                isWatched = isFavoriteState,
                                addWatched = {
                                    viewModel.addFavorite(it)
                                    coroutineScope.launch {
                                        snackBarHostState.showSnackbar(
                                            message = "Pelicula marcada como Vista",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                },
                                removeWatched = {
                                    viewModel.removeFavorite(it)
                                    coroutineScope.launch {
                                        snackBarHostState.showSnackbar(
                                            message = "Pelicula desmarcada como Vista",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                            )
                        }

                        item {
                            MovieCategorySection(
                                title = "Mas Valoradas",
                                icon = Icons.Default.ThumbUp,
                                iconColor = Color.Blue,
                                movies = topRatedMoviesState,
                                onTapMovie = { movieId ->
                                    navControllerAppNavigation.navigate("movie/$movieId")
                                },
                                onLongPressMovie = {
                                    movieId.value = it
                                    closeOtherMenus(listOf(popularExpandMenuIndex, upcomingExpandMenuIndex, nowPlayingExpandMenuIndex))
                                    viewModel.isFavorite(it)
                                },
                                expandMenuIndex = topRatedExpandMenuIndex,
                                onAddToList = {
                                    expandMenu.value = true
                                },
                                onShare = {
                                    coroutineScope.launch {
                                        viewModel.getMovie(it.toString())
                                            ?.let { it1 -> context.shareMovie(it1) }
                                    }
                                },
                                isWatched = isFavoriteState,
                                addWatched = {
                                    viewModel.addFavorite(it)
                                    coroutineScope.launch {
                                        snackBarHostState.showSnackbar(
                                            message = "Pelicula marcada como Vista",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                },
                                removeWatched = {
                                    viewModel.removeFavorite(it)
                                    coroutineScope.launch {
                                        snackBarHostState.showSnackbar(
                                            message = "Pelicula desmarcada como Vista",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
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

fun Context.shareMovie(movie: MovieDetailsUI) {
    val shareText = """
        🎬 *${movie.title}*
        
        📅 Fecha de estreno: ${movie.releaseDate}
        
        📝 Descripción: ${movie.overview}
        
        🌐 Más información: ${movie.homepage ?: "https://www.imdb.com/title/${movie.imdbID}"}
        
        ⭐️ Valoración: ${movie.voteAverage} / 10
        
        ¡Échale un vistazo! 🍿
    """.trimIndent()

    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }
    startActivity(Intent.createChooser(shareIntent, "Compartir película"))
}
