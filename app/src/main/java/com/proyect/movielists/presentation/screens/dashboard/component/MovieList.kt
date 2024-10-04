package com.proyect.movielists.presentation.screens.dashboard.component

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.proyect.movielists.presentation.models.MovieUI


@Composable
fun MovieList(
    listMovies: List<MovieUI>?,
    onTap: (Int) -> Unit,
    onLongPress: (Int) -> Unit
){
    LazyVerticalGrid(GridCells.Fixed(2)) {
        listMovies?.forEach {
            item {
                ItemMovie(
                    movie = it,
                    onTap = onTap,
                    onLongPress = onLongPress
                )
            }
        }
    }
}


@Composable
fun ItemMovie(
    movie: MovieUI?,
    onTap: (Int) -> Unit,
    onLongPress: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        onLongPress(movie?.id ?: 0)
                    },
                    onTap = {
                        onTap(movie?.id ?: 0)
                    }
                )
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = "https://image.tmdb.org/t/p/w500${movie?.backdropUrl}",
                    contentDescription = "Portada Product"
                )
            }

            movie?.title?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}