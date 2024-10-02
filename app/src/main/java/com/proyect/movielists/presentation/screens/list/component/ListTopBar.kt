package com.proyect.movielists.presentation.screens.list.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTopBar(
    onBackClick: () -> Unit,
    onRemoveList: () -> Unit
){

    val expandMenu = remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(text = "Lista de Películas") },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                expandMenu.value = !expandMenu.value
            }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Más opciones"
                )
                DropdownMenu(
                    expanded = expandMenu.value,
                    onDismissRequest = {
                        expandMenu.value = false
                    }
                ) {
                    DropdownMenuItem(
                        text = { Text("Eliminar lista") },
                        onClick = {
                            onRemoveList()
                            onBackClick()
                        }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.surface) ,
    )
}