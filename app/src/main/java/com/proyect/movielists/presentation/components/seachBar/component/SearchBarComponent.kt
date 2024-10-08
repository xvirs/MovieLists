package com.proyect.movielists.presentation.components.seachBar.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.proyect.movielists.presentation.models.MovieUI

@Composable
fun ItemMovieSearching(
    movie: MovieUI?,
    getMovieID: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable { getMovieID(movie?.id ?: 0) }
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp) // Tamaño del cuadrado para la imagen
            ) {
                if (movie != null) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${movie.posterUrl}",
                        contentDescription = "Portada Movie",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            movie?.title?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Default.NavigateNext,
                contentDescription = "Delete Task",
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp)
                    .clickable {
//                        removeTask(task)
                    }
            )
        }
    }
}