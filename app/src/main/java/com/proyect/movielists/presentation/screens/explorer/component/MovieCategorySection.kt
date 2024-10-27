package com.proyect.movielists.presentation.screens.explorer.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.proyect.movielists.presentation.models.MovieUI

@Composable
fun MovieCategorySection(
    title: String,
    icon: ImageVector,
    iconColor: Color? = null,
    movies: List<MovieUI>,
    onTapMovie: (Int) -> Unit,
    onLongPressMovie: (Int) -> Unit,
    expandMenuIndex: MutableState<Int>,
    onAddToList: (Int) -> Unit,
    onShare: (Int) -> Unit,
    isWatched: Boolean,
    addWatched: (Int) -> Unit,
    removeWatched: (Int) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "$title Icon",
                modifier = Modifier.size(24.dp),
                tint = (iconColor ?: Color.Unspecified)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        MovieList(
            listMovies = movies,
            onTap = onTapMovie,
            onLongPress = onLongPressMovie,
            xpandMenuIndex = expandMenuIndex,
            onAddToList = { onAddToList(it) },
            onShare = { onShare(it) },
            isWatched = isWatched,
            addWatched = { addWatched(it) },
            removeWatched = { removeWatched(it) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        HorizontalDivider()
    }
}