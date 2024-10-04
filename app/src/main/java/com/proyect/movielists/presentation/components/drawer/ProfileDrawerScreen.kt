package com.proyect.movielists.presentation.components.drawer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.proyect.movielists.presentation.components.drawer.component.ErrorComponent
import com.proyect.movielists.presentation.components.drawer.component.ProfileContent
import com.proyect.movielists.presentation.components.shared.Loading
import com.proyect.movielists.presentation.models.ProfileUI
import com.proyect.movielists.utils.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun DrawerProfile(
    navControllerAppNavigation: NavHostController
) {
    val viewModel: ProfileViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is UIState.Loading -> Loading()
        is UIState.Success -> ProfileContent(profile = (uiState as UIState.Success<ProfileUI>).data) {
            navControllerAppNavigation.navigate("login") {
                popUpTo(navControllerAppNavigation.graph.startDestinationId) {
                    inclusive = true
                }
            }

        }

        is UIState.Error -> ErrorComponent(message = (uiState as UIState.Error).message)
        else -> {}
    }
}