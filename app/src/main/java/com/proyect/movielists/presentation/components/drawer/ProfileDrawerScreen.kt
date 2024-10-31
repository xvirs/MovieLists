package com.proyect.movielists.presentation.components.drawer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
    coroutineScope: CoroutineScope,
    setGesturesEnabled: (Boolean) -> Unit = {}
) {
    val viewModel: ProfileViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val isLoggingOut by viewModel.isLoggingOut.collectAsState()

    when (uiState) {
        is UIState.Loading -> {
            Box(modifier = Modifier
                .fillMaxSize()){
                Loading()
            }
        }
        is UIState.Success -> ProfileContent(
            profile = (uiState as UIState.Success<ProfileUI>).data,
            closeSession = {
                coroutineScope.launch {
                    setGesturesEnabled(false)
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
            },
            isLoggingOut = isLoggingOut
        )
        is UIState.Error -> ErrorComponent(message = (uiState as UIState.Error).message)
        else -> {}
    }
}