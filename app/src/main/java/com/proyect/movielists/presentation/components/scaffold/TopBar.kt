package com.proyect.movielists.presentation.components.scaffold

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(snackBarHostState: SnackbarHostState, coroutineScope: CoroutineScope, drawerState: DrawerState) {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!active) {
            IconButton(
                modifier = Modifier
                    .padding(bottom = 4.dp),
                onClick = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile"
                )
            }
        }

        SearchBar(
            query = text,
            onQueryChange = { text = it },
            onSearch = {
                active = false
            },
            active = active,
            onActiveChange = { active = it },
        ) {}
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMoviesWithDrawer() {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // El contenido del Drawer
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Contenido del Drawer
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(text = "Opción 1", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Opción 2", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Opción 3", style = MaterialTheme.typography.titleMedium)
            }
        },
        content = {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icono de Menú para abrir el Drawer
                IconButton(
                    onClick = {
                        scope.launch { drawerState.open() } // Abre el Drawer cuando se presiona el icono
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Open Drawer"
                    )
                }

                // SearchBar
                SearchBar(
                    query = text,
                    onQueryChange = { text = it },
                    onSearch = {
                        active = false
                    },
                    active = active,
                    onActiveChange = { active = it },
                ) {
                    // Contenido adicional dentro de la SearchBar (opcional)
                }
            }
        }
    )
}