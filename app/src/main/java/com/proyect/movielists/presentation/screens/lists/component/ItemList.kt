package com.proyect.movielists.presentation.screens.lists.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.proyect.movielists.domine.models.ListItem

@Composable
fun ItemList(
    listItem: ListItem,
    getListID: (Int) -> Unit,
    removeList: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { getListID(listItem.id) },
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(4f)) {
                Text(
                    text = listItem.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1, overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = listItem.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2, overflow = TextOverflow.Ellipsis
                )
                Row {
                    Icon(imageVector = Icons.Default.Movie, contentDescription = "Movies")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${listItem.itemCount} películas")
                }
            }
            IconButton(onClick = { removeList(listItem.id) }) {
                Icon(imageVector = Icons.Default.DeleteOutline, contentDescription = "Eliminar lista")
            }
        }
    }
}
