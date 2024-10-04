package com.proyect.movielists.presentation.screens.dashboard.component

import androidx.compose.runtime.Composable
import com.proyect.movielists.presentation.models.MovieUI

@Composable
fun MoviesContent(
    movies: List<MovieUI>,
    onTapMovie: (Int) -> Unit,
    onLongPressMovie: (Int) -> Unit
) {
    MovieList(
        listMovies = movies,
        onTap = onTapMovie,
        onLongPress = onLongPressMovie
    )
}