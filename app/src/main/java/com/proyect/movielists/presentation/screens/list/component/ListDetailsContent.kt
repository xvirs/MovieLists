package com.proyect.movielists.presentation.screens.list.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.proyect.movielists.presentation.models.MovieListUI
import com.proyect.movielists.presentation.models.MovieUI

@Composable
fun ListDetailsContent(
    listDetails: MovieListUI,
    watchedMovies: List<MovieUI>,
    unwatchedMovies: List<MovieUI>,
    onRemoveMovie: (String) -> Unit,
    navigateToMovie: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Box(modifier = Modifier.heightIn(max = 200.dp)) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        Text(
                            text = listDetails.description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (listDetails.movies.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Movie,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = "Empty List",
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Tu lista está vacía.",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Agrega películas para empezar a disfrutar.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }

            } else {
                MovieTabComponent(
                    watchedMovies = watchedMovies,
                    unwatchedMovies = unwatchedMovies,
                    onRemoveMovie = {onRemoveMovie(it)},
                    navigateToMovie = { navigateToMovie(it) }
                )
            }
        }
    }
}


@Composable
fun MovieTabComponent(
    watchedMovies: List<MovieUI>,
    unwatchedMovies: List<MovieUI>,
    onRemoveMovie: (String) -> Unit,
    navigateToMovie: (Int) -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("No Vistas", "Vistas")

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }
        when (selectedTabIndex) {
            0 -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(unwatchedMovies) { movie ->
                        MovieItem(
                            movie = movie,
                            onRemoveMovie = { onRemoveMovie(movie.id.toString()) },
                            navigateToMovie = { navigateToMovie(it) }
                        )
                    }
                }
            }
            1 -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(watchedMovies) { movie ->
                        MovieItem(
                            movie = movie,
                            onRemoveMovie = { onRemoveMovie(movie.id.toString()) },
                            navigateToMovie = { navigateToMovie(it) }
                        )
                    }
                }
            }
        }
    }
}
