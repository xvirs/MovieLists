package com.proyect.movielists.presentation.screens.movie.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.proyect.movielists.presentation.components.shared.AddMovieToListDialog
import com.proyect.movielists.presentation.models.ListItemUI

@Composable
fun MovieBottomBar(
    movieId: Int,
    isFavorite: () -> Boolean,
    movieList: List<ListItemUI>,
    onFavoriteClick: () -> Unit,
    onAddToListClick: (Int, Int) -> Unit,
    onCreateNewListClick: (String, String, Int) -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }

    BottomAppBar(
        contentPadding = PaddingValues(horizontal = 40.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onFavoriteClick() }) {
                Icon(
                    imageVector = if (isFavorite()) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Mark as Favorite",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            IconButton(onClick = { showDialog.value = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add to List",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share with friends",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }

    if (showDialog.value) {
        AddMovieToListDialog(
            movieId = movieId,
            movieList = movieList,
            onAddToListClick = onAddToListClick,
            onCreateNewListClick = onCreateNewListClick,
            onDismiss = { showDialog.value = false }
        )
    }
}



