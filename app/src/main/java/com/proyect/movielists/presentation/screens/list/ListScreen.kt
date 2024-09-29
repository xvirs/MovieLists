package com.proyect.movielists.presentation.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import com.proyect.movielists.domine.models.GetMovieListResponse
import com.proyect.movielists.domine.models.MovieItem
import com.proyect.movielists.utils.UiState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    listId: String,
    onBackClick: () -> Unit
) {
    val viewModel: ListViewModel = koinViewModel()
    val listDetailsState by viewModel.listDetailsState.collectAsState()
    val successMessage by viewModel.successMessage.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val expandMenu = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getListDetails(listId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lista de Películas") },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        expandMenu.value = !expandMenu.value
                    }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Más opciones"
                        )
                        DropdownMenu(
                            expanded = expandMenu.value,
                            onDismissRequest = {
                                expandMenu.value = false
                            }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Eliminar lista") },
                                onClick = {
                                    viewModel.removeList(listId.toInt())
                                    onBackClick()
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.surface) ,
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(padding)
            ) {
                when (listDetailsState) {
                    is UiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    is UiState.Success -> {
                        val listDetails = (listDetailsState as UiState.Success<GetMovieListResponse>).data
                        ListDetailsContent(
                            listDetails = listDetails,
                            onRemoveMovie = { movieId -> viewModel.removeMovieFromList(listId, movieId) },
                            onRemoveList = { viewModel.removeList(listId.toInt()) }
                        )
                    }
                    is UiState.Error -> {
                        val errorMessage = (listDetailsState as UiState.Error).message
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                if (successMessage.isNotEmpty()) {
                    // Mostrar snackbar de éxito
                }

                if (errorMessage.isNotEmpty()) {
                    // Mostrar snackbar de error
                }
            }
        }
    )
}

@Composable
fun ListDetailsContent(
    listDetails: GetMovieListResponse,
    onRemoveMovie: (String) -> Unit,
    onRemoveList: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = listDetails.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = listDetails.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (listDetails.movies.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "La lista está vacía",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(listDetails.movies) { movie ->
                        MovieItem(
                            movie = movie,
                            onRemoveMovie = { onRemoveMovie(movie.id.toString()) }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun MovieItem(
    movie: MovieItem,
    onRemoveMovie: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = movie.title ?: "Sin título",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        IconButton(onClick = onRemoveMovie) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar película")
        }
    }
}