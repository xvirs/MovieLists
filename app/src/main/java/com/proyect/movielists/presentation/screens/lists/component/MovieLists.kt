package com.proyect.movielists.presentation.screens.lists.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.proyect.movielists.domine.models.ListItem

@Composable
fun MovieLists(
    moviesLists: List<ListItem> = emptyList(),
    getListID: (Int) -> Unit,
    removeList: (Int) -> Unit
) {
    if(moviesLists.isEmpty()){
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(text = "No hay listas")
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(moviesLists.size) { index ->
                val listItem = moviesLists[index]
                ItemList(listItem = listItem, getListID = getListID, removeList = removeList)
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}