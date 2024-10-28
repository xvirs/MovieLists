package com.proyect.movielists.presentation.components.drawer

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.proyect.movielists.presentation.components.drawer.component.ErrorComponent
import com.proyect.movielists.presentation.components.drawer.component.ProfileContent
import com.proyect.movielists.presentation.components.shared.Loading
import com.proyect.movielists.presentation.models.ProfileUI
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun DrawerProfile(
    navControllerAppNavigation: NavHostController,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    val viewModel: ProfileViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is UIState.Loading -> Loading()
        is UIState.Success -> ProfileContent(profile = (uiState as UIState.Success<ProfileUI>).data) {
            coroutineScope.launch {
                snackBarHostState.showSnackbar(
                    message = viewModel.deleteSession(),
                    withDismissAction = true,
                    duration = SnackbarDuration.Short
                )
                navControllerAppNavigation.navigate("login") {
                    popUpTo(navControllerAppNavigation.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
        }

        is UIState.Error -> ErrorComponent(message = (uiState as UIState.Error).message)
        else -> {}
    }
}