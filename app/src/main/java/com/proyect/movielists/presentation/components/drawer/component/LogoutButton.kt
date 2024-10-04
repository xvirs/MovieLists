package com.proyect.movielists.presentation.components.drawer.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LogoutButton(
    navigateToLogin: () -> Unit
) {
    Button(
        onClick = {
            navigateToLogin()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text(text = "Cerrar Sesión", style = MaterialTheme.typography.bodyMedium)
    }
}