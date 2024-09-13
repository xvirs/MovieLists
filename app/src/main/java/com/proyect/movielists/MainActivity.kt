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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieListsTheme(
                dynamicColor = true,
            ) {val navController = rememberNavController()
                AppNavigation(navController)

//                Scaffold()
//                if (isLoading){
//                    Loading()
//                } else {
//                    AppNavigation(navController)
//                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieListsTheme {
        Greeting("Android")
    }
}