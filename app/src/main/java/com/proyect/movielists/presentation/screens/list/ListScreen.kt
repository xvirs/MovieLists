package com.proyect.movielists.presentation.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.proyect.movielists.presentation.components.shared.Loading
import com.proyect.movielists.presentation.models.MovieListUI
import com.proyect.movielists.presentation.screens.list.component.ListDetailsContent
import com.proyect.movielists.presentation.screens.list.component.ListTopBar
import com.proyect.movielists.utils.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListScreen(
    navControllerAppNavigation: NavHostController,
    listId: String,
    onBackClick: () -> Unit
) {
    val viewModel: ListViewModel = koinViewModel()
    val listDetailsState by viewModel.listDetailsState.collectAsState()
    val watchedMovies by viewModel.watchedMovies.collectAsState()
    val unwatchedMovies by viewModel.unwatchedMovies.collectAsState()
    val successMessage by viewModel.successMessage.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getListDetails(listId)
    }

    Scaffold(
        topBar = {
            if (listDetailsState is UIState.Success) {
                ListTopBar(
                    title = (listDetailsState as UIState.Success<MovieListUI>).data.name,
                    onBackClick = onBackClick,
                    onRemoveList = { viewModel.removeList(listId.toInt()) }
                )
            }
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                when (listDetailsState) {
                    is UIState.Loading -> Loading()
                    is UIState.Success -> {
                        val listDetails = (listDetailsState as UIState.Success<MovieListUI>).data
                        ListDetailsContent(
                            listDetails = listDetails,
                            watchedMovies = watchedMovies,
                            unwatchedMovies = unwatchedMovies,
                            onRemoveMovie = { movieId -> viewModel.removeMovieFromList(listId, movieId) },
                            navigateToMovie = {
                                navControllerAppNavigation.navigate("movie/$it")
                            }
                        )
                    }
                    is UIState.Error -> {
                        val errorMessage = (listDetailsState as UIState.Error).message
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background),
                            textAlign = TextAlign.Center
                        )
                    }
                    UIState.Idle -> {}
                }
            }
        }
    )
}