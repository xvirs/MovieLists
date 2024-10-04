package com.proyect.movielists.presentation.components.seachBar.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.proyect.movielists.presentation.models.MovieUI

@Composable
fun SearchMovieList(
    listMovies: List<MovieUI>?,
    getMovieID: (Int) -> Unit
) {
    LazyColumn() {
        listMovies?.forEach { movie ->
            movie.let {
                item {
                    ItemMovieSearching(
                        movie = movie,
                        getMovieID = getMovieID
                    )
                }
            }
        }
    }
}