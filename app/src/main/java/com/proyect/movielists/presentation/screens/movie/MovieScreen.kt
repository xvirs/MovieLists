package com.proyect.movielists.presentation.screens.movie


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.project.mytemplate.presentation.components.utils.Loading
import com.proyect.movielists.domine.models.MovieDetails
import com.proyect.movielists.utils.UiState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(
    movieId: String,
    onBackPress: () -> Unit,
) {
    val viewModel: MovieViewModel = koinViewModel()
    val movieState by viewModel.movieState.collectAsState()
    val movieList by viewModel.moviesLists.collectAsState()
    val expandMenu = remember { mutableStateOf(false) }

    LaunchedEffect(movieId) {
        viewModel.getMovie(movieId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Título vacío ya que lo eliminamos de la barra superior */ },
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors =  TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background),
            )
        },
        bottomBar = {
            BottomAppBar(
                contentPadding = PaddingValues(horizontal = 40.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        viewModel.addFavorite(movieId.toInt())
                    }) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Mark as Favorite",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }

                    Box {
                        IconButton(onClick = {
                            expandMenu.value = !expandMenu.value
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add to List",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        DropdownMenu(
                            expanded = expandMenu.value,
                            onDismissRequest = { expandMenu.value = false }
                        ) {
                            movieList.forEach { list ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = list.name,
                                            maxLines = 1,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    },
                                    onClick = {
                                        viewModel.addMovieToList(
                                            list.id.toString(), movieId.toInt()
                                        )
                                        expandMenu.value = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (movieState) {
                is UiState.Loading -> {
                    Loading()
                }

                is UiState.Success -> {
                    val movie = (movieState as UiState.Success<MovieDetails>).data
                    val imageUrl = "https://image.tmdb.org/t/p/w500${movie.backdropPath}"

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = movie.title ?: "Unknown title",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        // Imagen de la película
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "Movie Poster",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        RatingStars(voteAverage = movie.voteAverage)

                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .verticalScroll(rememberScrollState()), // Hace que sea deslizable
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(0.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = movie.overview ?: "No description available.",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(0.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
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
                            }
                        }
                    }
                }

                is UiState.Error -> {
                    Text(
                        text = (movieState as UiState.Error).message,
                        color = Color.Red
                    )
                }
            }
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
