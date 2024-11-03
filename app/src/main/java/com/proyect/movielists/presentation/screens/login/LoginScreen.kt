package com.proyect.movielists.presentation.screens.login

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyect.movielists.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    val viewModel: AuthViewModel = koinViewModel()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(uiState is UIState.Loading ) {
        if(uiState is UIState.Loading){
            isLoading = true
        }
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is UIState.Error -> {
                val errorMessage = (uiState as UIState.Error).message
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(
                        message = errorMessage,
                        withDismissAction = true
                    )
                }
                isLoading = false
            }
            is UIState.Loading -> {
                isLoading = true
            }
            is UIState.Success -> {
                navController.navigate("main") {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }
            else -> Unit
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(0.4f))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Gestiona tus películas favoritas",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .width(100.dp)
                            .padding(vertical = 8.dp),
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Crea listas personalizadas, lleva un registro de películas que has visto, explora nuevas películas y más.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                            lineHeight = 28.sp,
                            fontSize = 18.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(0.5f))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = false
                        },
                        label = {
                            if (emailError) {
                                Text("El campo email es obligatorio")
                            } else {
                                Text("Usuario")
                            }
                        },
                        isError = emailError,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading
                    )
                    if (emailError) {
                        Text(
                            text = "",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = false
                        },
                        label = {
                            if (emailError) {
                                Text("El campo contraseña es obligatorio")
                            } else {
                                Text("contraseña")
                            }
                        },
                        isError = passwordError,
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(
                                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                                )
                            }
                        },
                        enabled = !isLoading
                    )
                    if (passwordError) {
                        Text(
                            text = "",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            emailError = email.isBlank()
                            passwordError = password.isBlank()
                            if (!emailError && !passwordError) {
//                                throw RuntimeException("Test Crash") // Force a crash
                                viewModel.login(email, password)
                            }
                        },
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
                            Text("Iniciar sesión")
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "¿No tienes una cuenta? Regístrate en TMDB",
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.clickable {
                            val registerUrl = "https://www.themoviedb.org/signup"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(registerUrl))
                            context.startActivity(intent)
                        }
                    )
                }

                // Espacio flexible entre el formulario y la sección de agradecimiento
                Spacer(modifier = Modifier.weight(1f))

                // Sección inferior: agradecimiento a TMDB
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Datos obtenidos de",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Image(
                        painter = painterResource(id = R.drawable.tmdb_image),
                        contentDescription = "TMDB Logo",
                        modifier = Modifier
                            .height(40.dp)
                            .padding(top = 8.dp)
                    )
                }
            }
        }

    }
}
