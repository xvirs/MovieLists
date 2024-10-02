package com.proyect.movielists.presentation.screens.movie.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.proyect.movielists.domine.models.ListItem

@Composable
fun MovieBottomBar(
    movieList: List<ListItem>,
    onFavoriteClick: () -> Unit,
    onAddToListClick: (Int) -> Unit
) {
    val expandMenu = remember { mutableStateOf(false) }

    BottomAppBar(
        contentPadding = PaddingValues(horizontal = 40.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Mark as Favorite",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            Box {
                IconButton(onClick = { expandMenu.value = !expandMenu.value }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add to List",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                DropdownMenu(
                    expanded = expandMenu.value,
                    onDismissRequest = { expandMenu.value = false }
                ) {
                    movieList.forEach { list ->
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
                                onAddToListClick(list.id)
                                expandMenu.value = false
                            }
                        )
                    }
                }
            }
        }
    }
}
