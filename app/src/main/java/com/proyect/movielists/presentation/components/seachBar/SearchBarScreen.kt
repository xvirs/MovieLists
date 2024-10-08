package com.proyect.movielists.presentation.components.seachBar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.proyect.movielists.presentation.components.seachBar.component.SearchMovieList
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarScreen(
    navControllerAppNavigation: NavHostController,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    showIcon: () -> Unit
) {
    val viewModel: SearchBarViewModel = koinViewModel()
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val listMovies = viewModel.uiState.collectAsState()

    SearchBar(
        query = text,
        onQueryChange = {
            text = it
            viewModel.searchMovies(text)
        },
        onSearch = {
            active = false
        },
        active = active,
        onActiveChange = {
            showIcon()
            active = it
        },
    ) {
        SearchMovieList(
            listMovies = listMovies.value.let {
                if (it is UIState.Success) it.data else emptyList()
            },
            getMovieID = { movieId ->
                navControllerAppNavigation.navigate("movie/$movieId")
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(
                        message = movieId.toString(),
                        withDismissAction = true
                    )
                }
            }
        )
    }
}