package com.proyect.movielists.presentation.screens.lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.mytemplate.presentation.components.utils.Loading
import com.proyect.movielists.domine.models.ListItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun ListsScreen(
    navControllerAppNavigation: NavHostController,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    val viewModel: ListsViewModel = koinViewModel()
    val isLoading = viewModel.isLoading.collectAsState()
    val moviesLists = viewModel.moviesLists.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()
    val successMessage = viewModel.successMessage.collectAsState()

    LaunchedEffect(errorMessage.value.isNotEmpty() || successMessage.value.isNotEmpty()) {
        coroutineScope.launch {
            snackBarHostState.showSnackbar(
                message = errorMessage.value,
                withDismissAction = true
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
        if (isLoading.value){
            Loading()
        } else {
            if(moviesLists.value.isNotEmpty()){
                MovieLists(
                    moviesLists = moviesLists.value,
                    getListID = { listID ->
                        navControllerAppNavigation.navigate("list/$listID")
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(
                                message = listID.toString(),
                                withDismissAction = true
                            )
                        }
                    }
                )
            } else {
                Text(text = "No hay listas")
            }
        }

        FloatingActionButton(
            onClick = {
                viewModel.createMovieList("Nueva lista", "Descripción de la lista", "es")
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            androidx.compose.material.Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Note"
            )
        }
    }

}

@Composable
fun MovieLists(
    moviesLists : List<ListItem>,
    getListID: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp)
    ) {
        items(moviesLists.size) { index ->
            val listItem = moviesLists[index]
            ItemList(
                listItem = listItem,
                getListID = getListID
            )
        }
    }
}


@Composable
fun ItemList(
    listItem: ListItem,
    getListID: (Int) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                getListID(listItem.id)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(5f)
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = listItem.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .focusable(true)
                )
                Text(
                    text = listItem.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp, top = 6.dp)
                )
            }
            Icon(
                imageVector = Icons.Default.DeleteOutline,
                contentDescription = "Delete Task",
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp)
                    .clickable {
//                        removeTask(task)
                    }
            )
        }
    }
}