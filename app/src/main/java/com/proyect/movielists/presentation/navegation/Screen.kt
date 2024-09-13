package com.project.mytemplate.presentation.navegation

import androidx.annotation.StringRes
import com.proyect.movielists.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Profile : Screen("profile", R.string.ProfileScreen)
    object Movies : Screen("movies", R.string.MoviesScreen)
    object Screen3 : Screen("screen3", R.string.screen3)
}

