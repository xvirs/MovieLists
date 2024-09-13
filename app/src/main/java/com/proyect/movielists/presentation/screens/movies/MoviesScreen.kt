package com.proyect.movielists.presentation.screens.movies

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.project.mytemplate.presentation.components.utils.ShimmerAnimation
import com.proyect.movielists.domine.models.Movie
import com.proyect.movielists.utils.MovieListType
import org.koin.androidx.compose.koinViewModel
@Composable
fun MoviesScreen() {

    val viewModel: MoviesViewModel = koinViewModel()
    val isLoading = viewModel.isLoading.collectAsState(initial = false)
    val listMovies = viewModel.listMovies.collectAsState(initial = emptyList())
    val selectedMovieListType = remember { mutableStateOf(MovieListType.POPULAR) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
//            SearchMovies()
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
                MovieList(listMovies.value)
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
fun MovieList(listMovies: List<Movie>?){
    LazyVerticalGrid(GridCells.Fixed(2)) {
        listMovies?.forEach {
            item {
                ItemProduct(it)
            }
        }
    }
}

@Composable
fun ItemProduct(movie: Movie?){
    Card(
        modifier = Modifier
//            .height(200.dp)
            .padding(5.dp)
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
                    model = "https://image.tmdb.org/t/p/w500${movie?.backdropPath}",
                    contentDescription = "Portada Product"
                )
            }

            movie?.title?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)

                )
            }

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
                    .height(120.dp) // Tamaño del espacio para la imagen de portada
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
