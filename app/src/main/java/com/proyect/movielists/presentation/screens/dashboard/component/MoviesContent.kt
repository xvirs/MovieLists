package com.proyect.movielists.presentation.screens.dashboard.component

import androidx.compose.runtime.Composable
import com.proyect.movielists.domine.models.Movie

@Composable
fun MoviesContent(
    movies: List<Movie>,
    onTapMovie: (Int) -> Unit,
    onLongPressMovie: (Int) -> Unit
) {
    MovieList(
        listMovies = movies,
        onTap = onTapMovie,
        onLongPress = onLongPressMovie
    )
}