package com.proyect.movielists.presentation.screens.login

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.proyect.movielists.presentation.components.shared.Loading
import com.proyect.movielists.presentation.screens.login.component.LoginForm
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    val viewModel: AuthViewModel = koinViewModel()
    var email by remember { mutableStateOf("XavierRosales") }
    var password by remember { mutableStateOf("reyvax15408571") }
    val uiState by viewModel.uiState.collectAsState()
    var message by remember { mutableStateOf("") }

    LaunchedEffect(message) {
        if (message.isNotEmpty()) {
            coroutineScope.launch {
                snackBarHostState.showSnackbar(
                    message = message,
                    withDismissAction = true
                )
            }
        }
    }

    when (uiState) {
        is UIState.Loading -> Loading()
        is UIState.Success -> {
            LaunchedEffect(Unit) {
                navController.navigate("main"){
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
        }
        is UIState.Error -> {
            message = "Error al iniciar sesion"
            LoginForm(
                email = email,
                password = password,
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onLoginClick = {
                    viewModel.login(email, password)
                }
            )
        }

        UIState.Idle -> LoginForm(
            email = email,
            password = password,
            onEmailChange = { email = it },
            onPasswordChange = { password = it },
            onLoginClick = {
                viewModel.login(email, password)
            }
        )
    }
}