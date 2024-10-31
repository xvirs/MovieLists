package com.proyect.movielists.presentation.screens.movie

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.proyect.movielists.presentation.components.shared.Loading
import com.proyect.movielists.presentation.models.MovieDetailsUI
import com.proyect.movielists.presentation.screens.movie.component.MovieBottomBar
import com.proyect.movielists.presentation.screens.movie.component.MovieDetailsContent
import com.proyect.movielists.presentation.screens.movie.component.MovieTopBar
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MovieScreen(
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    movieId: String,
    onBackPress: () -> Unit,
) {
    val viewModel: MovieViewModel = koinViewModel()
    val movieState by viewModel.movieDetail.collectAsState()
    val movieList by viewModel.moviesLists.collectAsState()
    val isFavorite by viewModel.isWatched.collectAsState()
    val showCreateListDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(movieId) {
        viewModel.getMovie(movieId)
        viewModel.isWatched(movieId.toInt())
    }

    Scaffold(
        topBar = {
            if (movieState is UIState.Success) {
                val movie = (movieState as UIState.Success<MovieDetailsUI>).data
                MovieTopBar(
                    onBackPress = onBackPress,
                    title = movie.title
                )
            } else {
                MovieTopBar(
                    onBackPress = onBackPress,
                    title = "Cargando..."
                )
            }
        },
        bottomBar = {
            if (movieState is UIState.Success) {
                MovieBottomBar(
                    movieId = movieId.toInt(),
                    isWatched = { isFavorite },
                    movieList = movieList,
                    onWatchedClick = {
                        if (isFavorite) {
                            viewModel.removeWatched(movieId.toInt())
                            coroutineScope.launch {
                                snackBarHostState.showSnackbar(
                                    message = "Nunca viste esta pelicula",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        } else {
                            viewModel.addWatched(movieId.toInt())
                            coroutineScope.launch {
                                snackBarHostState.showSnackbar(
                                    message = "Agregada como Vista!",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    },
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
                        viewModel.createMovieList(title, description, "es", movieId.toInt())
                        showCreateListDialog.value = false
                    },
                    onShareClick = { context.shareMovie((movieState as UIState.Success<MovieDetailsUI>).data) }
                )
            }
        },
        content = { paddingValues ->
            when (movieState) {
                is UIState.Loading -> {
                    Loading()
                }
                is UIState.Success -> {
                    val movie = (movieState as UIState.Success<MovieDetailsUI>).data
                    MovieDetailsContent(movie, paddingValues)
                }
                is UIState.Error -> {
                    Text(
                        text = (movieState as UIState.Error).message,
                        color = Color.Red,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        textAlign = TextAlign.Center
                    )
                }
                UIState.Idle -> {
                    // Puedes manejar este estado si necesitas algo en particular aquí
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    )
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
