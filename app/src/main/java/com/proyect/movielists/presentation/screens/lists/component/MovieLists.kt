package com.proyect.movielists.presentation.screens.lists.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.Card
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.proyect.movielists.presentation.components.shared.Loading
import com.proyect.movielists.presentation.models.ListItemUI

@Composable
fun MovieLists(
    moviesLists: List<ListItemUI> = emptyList(),
    onItemClick: (Int) -> Unit,
    removeList: (Int, String) -> Unit
) {
    val itemDeleted = remember { mutableStateOf(0) }

    if (moviesLists.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.PlaylistAdd,
                    contentDescription = "No lists available",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "No hay listas disponibles.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "¡Crea una nueva lista ahora!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    } else {
        Column {

            if (moviesLists.isNotEmpty()){
                Spacer(modifier = Modifier.height(24.dp))

                Row {
                    Icon(
                        imageVector = Icons.Filled.ViewList,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "Watched Movies",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    Text(
                        text = "Listas Creadas",
                        style = MaterialTheme.typography.titleLarge.copy(),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }


            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(moviesLists.size) { index ->

                    val listItem = moviesLists[index]
                    val moviePosterInList = listItem.posterUrls ?: emptyList()

                    CustomListItem(
                        listItem = listItem,
                        posterUrl = moviePosterInList,
                        onItemClick = { onItemClick(listItem.id) },
                        onRemoveList = {
                            removeList(listItem.id, listItem.name)
                            itemDeleted.value = listItem.id
                        }
                    )
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}


@Composable
fun CustomListItem(
    listItem: ListItemUI,
    posterUrl: List<String>,
    onItemClick: (Int) -> Unit,
    onRemoveList: () -> Unit
) {
    val expandMenu = remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(enabled = !isLoading.value) { onItemClick(listItem.id) }
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = listItem.name,
                        style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { expandMenu.value = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Opciones"
                        )
                        DropdownMenu(
                            expanded = expandMenu.value,
                            onDismissRequest = { expandMenu.value = false }
                        ) {
                            DropdownMenuItem(
                                leadingIcon = { Icon(Icons.Filled.DeleteOutline, contentDescription = "Eliminar lista") },
                                text = { Text("Eliminar lista") },
                                onClick = {
                                    isLoading.value = true
                                    expandMenu.value = false
                                    onRemoveList()
                                    isLoading.value = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (posterUrl.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        posterUrl.take(5).forEach { url ->
                            Image(
                                painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${url}"),
                                contentDescription = "Imagen de película",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(70.dp)
                                    .height(100.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.surface)
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Lista vacía...",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Cant. de películas : ${listItem.itemCount}",
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.End),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (isLoading.value) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                Loading()
            }
        }
    }
}