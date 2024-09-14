package com.proyect.movielists.presentation.components.scaffold

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.proyect.movielists.presentation.components.seachBar.SearchBarScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun TopBar(
    navControllerAppNavigation: NavHostController,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState
) {
    var showIconv by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        if (showIconv) {
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
        SearchBarScreen(
            navControllerAppNavigation = navControllerAppNavigation,
            snackBarHostState = snackBarHostState,
            coroutineScope = coroutineScope,
            showIcon = { showIconv = !showIconv }
        )
    }
}