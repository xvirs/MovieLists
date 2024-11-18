package com.proyect.movielists

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.proyect.movielists.domine.usecase.CheckSessionUseCase
import com.proyect.movielists.presentation.components.shared.Loading
import com.proyect.movielists.presentation.navegation.AppNavigation
import com.proyect.movielists.presentation.ui.theme.MovieListsTheme
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {

    private val checkSessionUseCase: CheckSessionUseCase by inject()
    private var _isChecked = MutableStateFlow(false)
    private var _isSessionValid = MutableStateFlow<Boolean?>(null)
    val isChecked = _isChecked.asStateFlow()
    val isSessionValid = _isSessionValid.asStateFlow()

    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            _isSessionValid.value = checkSession()
            _isChecked.value = true
        }

        setContent {
            KoinAndroidContext {
                MovieListsTheme(dynamicColor = true) {
                    val isLoading by isChecked.collectAsState()
                    val sessionValid by isSessionValid.collectAsState()

                    if (!isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surfaceContainerLowest.copy()),
                        )
                    } else {
                        if (sessionValid == true) {
                            AppNavigation("main")
                        } else {
                            AppNavigation("login")
                        }
                    }
                }
            }
        }
    }

    private suspend fun checkSession(): Boolean {
        return withContext(Dispatchers.IO) {
            checkSessionUseCase.invoke()
        }
    }
}