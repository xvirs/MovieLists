package com.proyect.movielists.presentation.components.scaffold

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.PlaylistAddCheckCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.project.mytemplate.presentation.navegation.Screen

@Composable
fun BottomNavigationBar(navController: NavController, onBottomNavItemClick: () -> Unit) {
    var selectedItem by rememberSaveable { mutableIntStateOf(1) }

    val items = listOf(
        NavigationItem("Listas", Icons.Filled.PlaylistAddCheckCircle, Screen.Lists.route),
        NavigationItem("Explorar", Icons.Filled.Explore, Screen.Explorer.route),
        NavigationItem("Vistas", Icons.Filled.Visibility, Screen.Watched.route),
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    onBottomNavItemClick()
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

data class NavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)
