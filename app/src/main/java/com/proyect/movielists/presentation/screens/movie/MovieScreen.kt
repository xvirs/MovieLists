package com.proyect.movielists.presentation.screens.movie


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.proyect.movielists.presentation.components.shared.Loading
import com.proyect.movielists.presentation.models.MovieDetailsUI
import com.proyect.movielists.presentation.screens.movie.component.MovieBottomBar
import com.proyect.movielists.presentation.screens.movie.component.MovieDetailsContent
import com.proyect.movielists.presentation.screens.movie.component.MovieTopBar
import com.proyect.movielists.utils.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieScreen(
    movieId: String,
    onBackPress: () -> Unit,
) {
    val viewModel: MovieViewModel = koinViewModel()
    val movieState by viewModel.movieDetail.collectAsState()
    val movieList by viewModel.moviesLists.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.getMovie(movieId)
    }

    Scaffold(
        topBar = {
            MovieTopBar(onBackPress)
        },
        bottomBar = {
            MovieBottomBar(
                movieList = movieList,
                onFavoriteClick = { viewModel.addFavorite(movieId.toInt()) },
                onAddToListClick = { listId -> viewModel.addMovieToList(listId.toString(), movieId.toInt()) }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                when (movieState) {
                    is UIState.Loading -> {
                        Loading()
                    }

                    is UIState.Success -> {
                        val movie = (movieState as UIState.Success<MovieDetailsUI>).data
                        MovieDetailsContent(movie)
                    }

                    is UIState.Error -> {
                        Text(
                            text = (movieState as UIState.Error).message,
                            color = Color.Red
                        )
                    }

                    UIState.Idle -> TODO()
                }
            }
        }
    )
}
