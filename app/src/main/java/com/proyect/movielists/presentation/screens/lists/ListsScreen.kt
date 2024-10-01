package com.proyect.movielists.presentation.screens.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.project.mytemplate.presentation.components.utils.Loading
import com.proyect.movielists.domine.models.ListItem
import com.proyect.movielists.domine.models.MovieFav
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListsScreen(
    navControllerAppNavigation: NavHostController,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    val viewModel: ListsViewModel = koinViewModel()
    val isLoading = viewModel.isLoading.collectAsState()
    val moviesLists = viewModel.moviesLists.collectAsState()
    val favorites = viewModel.favorites.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()
    val successMessage = viewModel.successMessage.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var listTitle by remember { mutableStateOf("") }
    var listDescription by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
        if (isLoading.value){
            Loading()
        } else {
            if(moviesLists.value.isNotEmpty()){
                favorites.value?.let {
                    MovieLists(
                        favoriteList = it.results,
                        moviesLists = moviesLists.value,
                        getListID = { listID ->
                            navControllerAppNavigation.navigate("list/$listID")
                            coroutineScope.launch {
                                snackBarHostState.showSnackbar(
                                    message = listID.toString(),
                                    withDismissAction = true
                                )
                            }
                        },
                        removeList = { listID ->
                            viewModel.removeList(listID)
                        }
                    )
                }
            } else {
                Text(text = "No hay listas")
            }
            FloatingActionButton(
                onClick = {
                    showDialog = true
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp)
            ) {
                androidx.compose.material.Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Note"
                )
            }
        }

        if (showDialog) {
            CreateListDialog(
                listTitle = listTitle,
                listDescription = listDescription,
                onTitleChange = { listTitle = it },
                onDescriptionChange = { listDescription = it },
                onDismiss = { showDialog = false },
                onCreate = {
                    viewModel.createMovieList(listTitle, listDescription, "es")
                    showDialog = false
                }
            )
        }

    }
}

@Composable
fun MovieLists(
    favoriteList: List<MovieFav> = emptyList(),
    moviesLists: List<ListItem>,
    getListID: (Int) -> Unit,
    removeList: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp)
    ) {
        //Lista de Peliculas favoritas
        item {
            FavoriteMoviesCarousel(favoriteList)
        }
        items(moviesLists.size) { index ->
            val listItem = moviesLists[index]
            ItemList(
                listItem = listItem,
                getListID = getListID,
                removeList = removeList
            )
        }
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun CreateListDialog(
    listTitle: String,
    listDescription: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onCreate: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Crear nueva lista",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = listTitle,
                    onValueChange = onTitleChange,
                    label = { Text("Título de la lista") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "List Title"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                OutlinedTextField(
                    value = listDescription,
                    onValueChange = onDescriptionChange,
                    label = { Text("Descripción de la lista") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = "List Description"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onCreate() }
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Create List",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Crear",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cancel",
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Cancelar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}

@Composable
fun ItemList(
    listItem: ListItem,
    getListID: (Int) -> Unit,
    removeList: (Int) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                getListID(listItem.id)
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(4f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = listItem.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = listItem.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2, // Limita a 2 líneas la descripción
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Movie,
                        contentDescription = "Movies",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${listItem.itemCount} películas",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            IconButton(
                onClick = {
                    removeList(listItem.id)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = "Eliminar lista",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
    HorizontalDivider()
}


@Composable
fun FavoriteMoviesCarousel(
    favoriteMovies: List<MovieFav>,
    modifier: Modifier = Modifier
) {
    if (favoriteMovies.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No hay películas seleccionadas como favoritas.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    } else {
        var selectedIndex by remember { mutableStateOf(0) }

        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(favoriteMovies.size) { index ->
                    val movie = favoriteMovies[index]
                    MovieCarouselItem(
                        movie = movie,
                        modifier = Modifier
                            .width(200.dp)
                            .clickable { selectedIndex = index }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(favoriteMovies.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .padding(4.dp)
                            .background(
                                if (index == selectedIndex) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun MovieCarouselItem(
    movie: MovieFav,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = modifier
            .height(300.dp)
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterUrl}",
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = movie.releaseDate,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

