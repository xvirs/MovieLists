package com.proyect.movielists.presentation.screens.explorer.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Reorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.proyect.movielists.presentation.models.MovieUI


@Composable
fun MovieList(
    listMovies: List<MovieUI>,
    onTap: (Int) -> Unit,
    onLongPress: (Int) -> Unit,
    xpandMenuIndex: MutableState<Int>,
    onAddToList: (Int) -> Unit,
    onShare: (Int) -> Unit,
    isFavorite: Boolean,
    addFavorite: (Int) -> Unit,
    removeFavorite: (Int) -> Unit
) {
    val expandMenuIndex = remember { xpandMenuIndex }

    LazyRow(
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onPress = {
                expandMenuIndex.value = -1
            })
        }
    ) {
        listMovies.forEachIndexed { index, movie ->
            item {
                ItemMoviePoster(
                    movie = movie,
                    isMenuExpanded = expandMenuIndex.value == index,
                    onTap = onTap,
                    onLongPress = {
                        expandMenuIndex.value = index
                        onLongPress(it)
                    },
                    onDismissMenu = {
                        expandMenuIndex.value = -1
                    },
                    onAddToList = {
                        onAddToList(it)
                    },
                    onShare = {
                        onShare(it)
                    },
                    isFavorite = isFavorite,
                    addFavorite = { addFavorite(it) },
                    removeFavorite = { removeFavorite(it) }
                )
            }
        }
    }
}

@Composable
fun ItemMoviePoster(
    movie: MovieUI,
    isMenuExpanded: Boolean,
    onTap: (Int) -> Unit,
    onLongPress: (Int) -> Unit,
    onDismissMenu: () -> Unit,
    onAddToList: (Int) -> Unit,
    onShare: (Int) -> Unit,
    isFavorite: Boolean,
    addFavorite: (Int) -> Unit,
    removeFavorite: (Int) -> Unit
) {

    Box(
        modifier = Modifier
            .width(120.dp)
            .padding(vertical = 8.dp, horizontal = 8.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            onLongPress(movie.id ?: 0)
                        },
                        onTap = {
                            onTap(movie.id ?: 0)
                        }
                    )
                }
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${movie.posterUrl}",
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.67f)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        if (isMenuExpanded) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        color = Color.Black.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { }
            ) {
                QuickActionMenu(
                    onAddToList = {
                        onAddToList(movie.id)
                    },
                    isFavorite = isFavorite,
                    addFavorite = { addFavorite(movie.id) },
                    removeFavorite = { removeFavorite(movie.id) },
                    onShare = {
                        onShare(movie.id)
                    }
                )
            }
        }
    }
}

@Composable
fun QuickActionMenu(
    onAddToList: () -> Unit,
    onShare: () -> Unit,
    isFavorite: Boolean,
    addFavorite: () -> Unit,
    removeFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onAddToList) {
            Icon(
                imageVector = Icons.Default.Reorder,
                contentDescription = "Agregar a la lista",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        IconButton(onClick = {
            if(isFavorite){
                removeFavorite()
            } else {
                addFavorite()
            }
        }) {
            Icon(
                imageVector =
                if(isFavorite){
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
                contentDescription = "Marcar como favorito",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        IconButton(onClick = onShare ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Compartir",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}