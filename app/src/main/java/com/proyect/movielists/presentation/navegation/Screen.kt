package com.project.mytemplate.presentation.navegation

import androidx.annotation.StringRes
import com.proyect.movielists.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Lists : Screen("lists", R.string.ListsScreen)
    object Dashboard : Screen("dashboard", R.string.DashboardScreen)
    object Screen3 : Screen("screen3", R.string.screen3)
}

