package com.proyect.movielists

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.proyect.movielists.presentation.navegation.AppNavigation
import com.proyect.movielists.presentation.ui.theme.MovieListsTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinAndroidContext {
                MovieListsTheme(
                    dynamicColor = true,
                ) {
                    AppNavigation()
                }
            }
        }
    }
}