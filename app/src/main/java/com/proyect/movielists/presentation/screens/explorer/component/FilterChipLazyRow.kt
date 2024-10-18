package com.proyect.movielists.presentation.screens.explorer.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.proyect.movielists.utils.MovieListType

@Composable
fun FilterChipLazyRow(
    selectedMovieListType: MovieListType,
    onChipSelected: (MovieListType) -> Unit
) {
    val movieListTypes = MovieListType.entries.toTypedArray()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movieListTypes) { movieListType ->
            val isSelected = movieListType == selectedMovieListType
            FilterChip(
                onClick = { onChipSelected(movieListType) },
                label = {
                    Text(movieListType.name.replace("_", " ").capitalize())
                },
                selected = isSelected,
                leadingIcon = if (isSelected) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
            )
        }
    }
}