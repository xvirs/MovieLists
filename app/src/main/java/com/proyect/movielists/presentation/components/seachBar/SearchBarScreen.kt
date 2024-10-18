package com.proyect.movielists.presentation.components.seachBar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.proyect.movielists.presentation.components.seachBar.component.SearchMovieList
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarScreen(
    navControllerAppNavigation: NavHostController,
    searchActive: Boolean,
    onSearchActiveChange: (Boolean) -> Unit
) {
    val viewModel: SearchBarViewModel = koinViewModel()
    var text by remember { mutableStateOf("") }
    val listMovies = viewModel.listMovies.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    SearchBar(
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
        placeholder = { Text("Buscar...") },
        query = text,
        onQueryChange = {
            text = it
            viewModel.onSearchQueryChanged(text)
        },
        onSearch = {
            keyboardController?.hide()
            if (listMovies.value.isEmpty())
                onSearchActiveChange(false)
        },
        active = searchActive,
        onActiveChange = {
            onSearchActiveChange(it)
        },
    ) {
        SearchMovieList(
            listMovies = listMovies.value,
            getMovieID = { movieId ->
                navControllerAppNavigation.navigate("movie/$movieId")
            }
        )
    }
}