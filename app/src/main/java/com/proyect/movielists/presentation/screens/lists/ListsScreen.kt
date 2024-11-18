package com.proyect.movielists.presentation.screens.lists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.proyect.movielists.presentation.components.shared.CreateListDialog
import com.proyect.movielists.presentation.components.shared.Loading
import com.proyect.movielists.presentation.screens.lists.component.MovieLists
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListsScreen(
    viewModel: ListsViewModel,
    navControllerAppNavigation: NavHostController,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
) {
    var showDialog by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val movieLists = viewModel.moviesLists.collectAsState()


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (uiState)  {
            is UIState.Loading -> {
                Loading()
            }
            is UIState.Error -> {
                Text(text = (uiState as UIState.Error).message)
            }
            is UIState.Success -> {
                Column {
                    MovieLists(
                        moviesLists = movieLists.value,
                        onItemClick = { listID ->
                            navControllerAppNavigation.navigate("list/$listID")
                        },
                        removeList = { listID , listName->
                            coroutineScope.launch {
                                snackBarHostState.showSnackbar(
                                    message = viewModel.removeList(listID, listName),
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    )
                }
            }
            is UIState.Idle -> {}
        }
        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            androidx.compose.material.Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add List"
            )
        }

        if (showDialog) {
            CreateListDialog(
                onDismiss = { showDialog = false },
                onCreate = { title, description ->
                    coroutineScope.launch {
                        showDialog = false
                        snackBarHostState.showSnackbar(
                            message = viewModel.createMovieList(title, description, "es"),
                            withDismissAction = true,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            )
        }
    }
}