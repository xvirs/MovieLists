package com.proyect.movielists.presentation.screens.movie.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.proyect.movielists.presentation.models.MovieDetailsUI

@Composable
fun MovieDetailsContent(movie: MovieDetailsUI) {
    val imageUrl = "https://image.tmdb.org/t/p/w500${movie.backdropUrl}"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween, // Distribuye los elementos equitativamente
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        // Título de la película
        Text(
            text = movie.title ?: "Unknown title",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(bottom = 8.dp, )
        )

        // Imagen de la película
        AsyncImage(
            model = imageUrl,
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .weight(2f) // Asigna más peso a la imagen
        )

        // Espacio entre componentes
        Spacer(modifier = Modifier.height(16.dp))

        // Descripción de la película (solo esta parte es scrolleable)
        MovieOverview(overview = movie.overview ?: "No description available.", modifier = Modifier.weight(2f))

        Spacer(modifier = Modifier.height(16.dp))

        // Estrellas de rating
        RatingStars(voteAverage = movie.voteAverage)

        Spacer(modifier = Modifier.height(16.dp))

        // Información extra (fecha de lanzamiento y duración)
        movie.runtime?.let { MovieExtraInfo(releaseDate = movie.releaseDate, runtime = it) }
    }
}

@Composable
fun MovieOverview(overview: String, modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth(), // Asigna peso para que ocupe parte de la pantalla
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(modifier = Modifier.heightIn(max = 200.dp)) { // Limita la altura máxima de la descripción
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(
                    text = overview,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}



@Composable
fun MovieExtraInfo(releaseDate: String, runtime: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Release date: $releaseDate",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Runtime: $runtime mins",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun RatingStars(voteAverage: Double) {
    val rating = (voteAverage / 2).coerceIn(0.0, 5.0)
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(1f)
        ) {
            repeat(5) { index ->
                val icon = when {
                    index < rating.toInt() -> Icons.Default.Star
                    index < rating -> Icons.AutoMirrored.Filled.StarHalf
                    else -> Icons.Default.StarBorder
                }
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
            }

        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = String.format("%.1f", voteAverage / 2),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
