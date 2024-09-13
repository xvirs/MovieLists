package com.proyect.movielists.presentation.screens.Lists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.project.mytemplate.presentation.components.utils.Loading
import com.proyect.movielists.presentation.components.drawer.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.koinViewModel


@Composable
fun ListsScreen(
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    val viewModel: ProfileViewModel = koinViewModel()
    val isLoading = viewModel.isLoading.collectAsState()
    val profile = viewModel.profile.collectAsState()


    if (isLoading.value){
        Loading()
    } else {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = 40.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = profile.value?.avatar?.tmdb?.avatarPath,
                contentDescription = "Photo profile",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onBackground)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Nombre Completo: ${profile.value?.name ?: ""}",
                color = MaterialTheme.colorScheme.surfaceTint
            )
            Text(
                text = "Username: ${profile.value?.username ?: ""}",
                color = MaterialTheme.colorScheme.surfaceTint
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "País: ${profile.value?.country ?: ""}",
                color = MaterialTheme.colorScheme.surfaceTint
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                // Acción del botón
            }) {
                Text(text = "Logout")
            }
        }

    }

}

