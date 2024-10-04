package com.proyect.movielists.presentation.screens.dashboard.component

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import com.proyect.movielists.presentation.models.ListItemUI
import com.proyect.movielists.utils.UIState

@Composable
fun MovieListsDropdownMenu(
    movieListsState: UIState<List<ListItemUI>>,
    expandMenu: Boolean,
    onDismissMenu: () -> Unit,
    onMovieListSelected: (String) -> Unit
) {
    DropdownMenu(
        expanded = expandMenu,
        onDismissRequest = { onDismissMenu() }
    ) {
        when (movieListsState) {
            is UIState.Success -> {
                movieListsState.data.forEach { list ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = list.name,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        onClick = { onMovieListSelected(list.id.toString()) }
                    )
                }
            }
            is UIState.Error -> {
                Text(
                    text = movieListsState.message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> {
                // Manejar estados Loading o Idle
            }
        }
    }
}