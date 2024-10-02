package com.proyect.movielists.presentation.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.proyect.movielists.presentation.components.shared.Loading
import com.proyect.movielists.domine.models.GetMovieListResponse
import com.proyect.movielists.presentation.screens.list.component.ListDetailsContent
import com.proyect.movielists.presentation.screens.list.component.ListTopBar
import com.proyect.movielists.utils.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListScreen(
    listId: String,
    onBackClick: () -> Unit
) {
    val viewModel: ListViewModel = koinViewModel()
    val listDetailsState by viewModel.listDetailsState.collectAsState()
    val successMessage by viewModel.successMessage.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getListDetails(listId)
    }

    Scaffold(
        topBar = {
            ListTopBar(
                onBackClick = onBackClick,
                onRemoveList = { viewModel.removeList(listId.toInt()) }
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                when (listDetailsState) {
                    is UIState.Loading -> {
                        Loading()
                    }
                    is UIState.Success -> {

                        val listDetails = (listDetailsState as UIState.Success<GetMovieListResponse>).data
                        ListDetailsContent(
                            listDetails = listDetails,
                            onRemoveMovie = { movieId -> viewModel.removeMovieFromList(listId, movieId) }
                        )
                    }
                    is UIState.Error -> {
                        val errorMessage = (listDetailsState as UIState.Error).message
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background)
                                .padding(padding)
                        )
                    }
                    UIState.Idle -> {}
                }
            }
        }
    )
}