package com.proyect.movielists.presentation.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.mytemplate.presentation.components.utils.ShimmerAnimation
import com.proyect.movielists.presentation.components.utils.MovieList
import com.proyect.movielists.utils.MovieListType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(
    navControllerAppNavigation: NavHostController,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    val viewModel: DashboardViewModel = koinViewModel()
    val isLoading = viewModel.isLoading.collectAsState(initial = false)
    val movies by viewModel.movies.collectAsState(initial = emptyList())
    val movieLists by viewModel.movieLists.collectAsState(initial = emptyList())
    val selectedMovieListType = remember { mutableStateOf(MovieListType.POPULAR) }
    val expandMenu = remember { mutableStateOf(false) }
    val movieID = remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            FilterChipLazyRow(
                selectedMovieListType = selectedMovieListType.value,
                onChipSelected = { movieListType ->
                    selectedMovieListType.value = movieListType
                    viewModel.someAuthenticatedRequest(movieListType)
                }
            )
            if (isLoading.value) {
                ShimmerMovieListPlaceholder()
            } else {
                MovieList(
                    listMovies = movies,
                    onTap = { movieId ->
                        navControllerAppNavigation.navigate("movie/$movieId")
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(
                                message = movieId.toString(),
                                withDismissAction = true
                            )
                        }
                    },
                    onLongPress = {
                        movieID.value = it
                        expandMenu.value = true
                    }
                )
                DropdownMenu(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    expanded = expandMenu.value,
                    onDismissRequest = { expandMenu.value = false }
                ) {
                    movieLists.forEach { list ->
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
                                    list.id.toString(), movieID.value
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

@Composable
fun FilterChipLazyRow(
    selectedMovieListType: MovieListType,
    onChipSelected: (MovieListType) -> Unit
) {
    val movieListTypes = MovieListType.entries.toTypedArray()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movieListTypes) { movieListType ->
            val isSelected = movieListType == selectedMovieListType
            FilterChip(
                onClick = { onChipSelected(movieListType) },
                label = {
                    Text(movieListType.name.replace("_", " ").capitalize())
                },
                selected = isSelected,
                leadingIcon = if (isSelected) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
            )
        }
    }
}


@Composable
fun ShimmerMovieListPlaceholder() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        items(10) {
            ShimmerItemProductPlaceholder()
        }
    }
}

@Composable
fun ShimmerItemProductPlaceholder() {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ){
                ShimmerAnimation(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ){
                ShimmerAnimation(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }
}
