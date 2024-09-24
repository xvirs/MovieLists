package com.proyect.movielists.presentation.screens.movie


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.project.mytemplate.presentation.components.utils.Loading
import com.proyect.movielists.domine.models.MovieDetails
import com.proyect.movielists.utils.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieScreen(
    movieId: String,
    onBackPress: () -> Unit,
) {
    val viewModel: MovieViewModel = koinViewModel()
    val movieState by viewModel.movieState.collectAsState()
    LaunchedEffect(movieId) {
        viewModel.getMovie(movieId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        when (movieState) {
            is UiState.Loading -> {
                Loading()
            }
            is UiState.Success -> {
                val movie = (movieState as UiState.Success<MovieDetails>).data
                val imageUrl = "https://image.tmdb.org/t/p/w500${movie.backdropPath}"
                val posterUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
                val painter = rememberAsyncImagePainter(model = imageUrl)

                Box {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(16.dp)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                IconButton(
                                    onClick = { onBackPress() },
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBackIosNew,
                                        contentDescription = "Back",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }

                                // Card con título
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)),
                                    elevation = CardDefaults.cardElevation(0.dp)
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth(),
                                        text = movie.title,
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Card con descripción
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)),
                                elevation = CardDefaults.cardElevation(0.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = movie.overview ?: "No description available.",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Parte media: imagen del póster y géneros
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f), // Que ocupe el espacio restante
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Card(
                                modifier = Modifier,
                                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)),
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = posterUrl),
                                    contentDescription = "Movie Poster",
                                    modifier = Modifier
                                        .fillMaxHeight(1f)
                                        .padding(10.dp)
                                        .clip(RoundedCornerShape(16.dp)),
                                    contentScale = ContentScale.FillHeight // Para que se vea completa y no se deforme
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)),
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.padding(vertical = 16.dp).fillMaxHeight()
                                ) {
                                    RatingStars(voteAverage = movie.voteAverage)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Parte inferior: géneros, fecha, duración y botones de acción
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            // Card con géneros, fecha y duración
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)),
                                elevation = CardDefaults.cardElevation(0.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = "Release date: ${movie.releaseDate}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Runtime: ${movie.runtime} mins",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Adult: ${if (movie.adult) "Yes" else "No"}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Botones de acción
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 40.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { /* Acción para añadir a lista */ }) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add to List",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                                IconButton(onClick = { /* Acción para marcar como favorito */ }) {
                                    Icon(
                                        imageVector = Icons.Default.FavoriteBorder,
                                        contentDescription = "Mark as Favorite",
                                        tint = MaterialTheme.colorScheme.secondary
                                    )
                                }
                            }
                        }
                    }
                }
            }
            is UiState.Error -> {
                val errorMessage = (movieState as UiState.Error).message
                Text(text = errorMessage, color = Color.Red)
            }
        }
    }
}

// Función para mostrar estrellas
@Composable
fun RatingStars(voteAverage: Double) {
    val rating = (voteAverage / 2).coerceIn(0.0, 5.0)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize(1f)
    ) {
        Text(
            text = String.format("%.1f", voteAverage/2),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
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
}
