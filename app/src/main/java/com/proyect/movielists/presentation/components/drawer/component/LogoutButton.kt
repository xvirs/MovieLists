package com.proyect.movielists.presentation.components.drawer.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LogoutButton(
    navigateToLogin: () -> Unit,
    isLoggingOut: Boolean
) {

    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(isLoggingOut) {
        if(isLoggingOut){
            isLoading = true
        }
    }
    Button(
        onClick = navigateToLogin,
        modifier = Modifier.fillMaxWidth(),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp
            )
        } else {
            Text("Cerrar sesión")
        }
    }
}