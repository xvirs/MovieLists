package com.proyect.movielists.presentation.screens.lists.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Crear nueva lista", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) },
        text = {
            Column {
                OutlinedTextField(
                    value = listTitle,
                    onValueChange = { listTitle = it },
                    label = { Text("Título de la lista") },
                    leadingIcon = { Icon(imageVector = Icons.Default.List, contentDescription = "List Title") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
                OutlinedTextField(
                    value = listDescription,
                    onValueChange = { listDescription = it },
                    label = { Text("Descripción de la lista") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Description, contentDescription = "List Description") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onCreate(listTitle, listDescription) }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Create List")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Crear")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Cancel")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cancelar")
            }
        }
    )
}
