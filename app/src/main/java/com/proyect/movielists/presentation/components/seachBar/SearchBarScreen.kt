package com.proyect.movielists.presentation.components.seachBar

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarScreen(showIcon: () -> Unit) {

    val viewModel: SearchBarViewModel = koinViewModel()
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val listMovies = viewModel.listMovies.collectAsState()

    SearchBar(
        query = text,
        onQueryChange = {
            text = it
            viewModel.someAuthenticatedRequest(text)
        },
        onSearch = {
            active = false
        },
        active = active,
        onActiveChange = {
            active = it
        },
    ) {

        Column {
            showIcon()
            listMovies.value?.map {
                Text(text = it.title)
            }
        }
    }
}