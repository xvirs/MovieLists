package com.proyect.movielists.presentation.components.drawer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.proyect.movielists.domine.models.UserProfile
import com.proyect.movielists.presentation.components.drawer.component.ErrorComponent
import com.proyect.movielists.presentation.components.drawer.component.ProfileContent
import com.proyect.movielists.presentation.components.shared.Loading
import com.proyect.movielists.utils.UIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun DrawerProfile() {
    val viewModel: ProfileViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is UIState.Loading -> Loading()
        is UIState.Success -> ProfileContent(profile = (uiState as UIState.Success<UserProfile>).data)
        is UIState.Error -> ErrorComponent(message = (uiState as UIState.Error).message)
        else -> {}
    }
}
