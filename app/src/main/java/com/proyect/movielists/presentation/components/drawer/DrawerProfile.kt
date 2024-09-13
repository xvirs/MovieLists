package com.proyect.movielists.presentation.components.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.project.mytemplate.presentation.components.utils.Loading
import org.koin.androidx.compose.koinViewModel

@Composable
fun DrawerProfile() {
    val viewModel: ProfileViewModel = koinViewModel()
    val isLoading = viewModel.isLoading.collectAsState()
    val profile = viewModel.profile.collectAsState()

    if (isLoading.value) {
        Loading()
    } else {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
                .fillMaxWidth(0.7f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${profile.value?.avatar?.tmdb?.avatarPath}",
                contentDescription = "Photo profile",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = profile.value?.name ?: "Sin nombre",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "@${profile.value?.username ?: "username"}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(80.dp))
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "País: ${profile.value?.country ?: "Desconocido"}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    // Acción del botón de logout
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(text = "Cerrar Sesión", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}
