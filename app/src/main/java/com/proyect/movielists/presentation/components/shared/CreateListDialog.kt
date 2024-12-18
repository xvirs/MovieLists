package com.proyect.movielists.presentation.components.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CreateListDialog(
    onDismiss: () -> Unit,
    onCreate: (String, String) -> Unit
) {
    var listTitle by remember { mutableStateOf("") }
    var listDescription by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    LaunchedEffect(listTitle.isNotEmpty()) {
        error = false
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text("Crear nueva lista",
                style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurface)) },
        text = {
            Column {
                OutlinedTextField(
                    value = listTitle,
                    onValueChange = { listTitle = it },
                    label = { Text("Título de la lista") },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth(),
                    isError = error,
                    trailingIcon = {
                        if (error) {
                            Icon(
                                imageVector = Icons.Filled.Error,
                                tint = MaterialTheme.colorScheme.error,
                                contentDescription = "Error name List",
                                modifier = Modifier
                            )
                        }
                    }

                )

                OutlinedTextField(
                    value = listDescription,
                    onValueChange = { listDescription = it },
                    label = { Text("Descripción de la lista") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    minLines = 5,
                    maxLines = 5,
                    shape = RoundedCornerShape(10.dp)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if(listTitle.isNotEmpty()){
                        onCreate(listTitle, listDescription)
                    } else {
                        error = true
                    }
                }
            ) {
                Text(
                    text = "Crear",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "Cancelar",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}