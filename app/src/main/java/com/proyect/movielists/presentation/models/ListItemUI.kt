package com.proyect.movielists.presentation.models

data class ListItemUI(
    val description: String,
    val favoriteCount: Int,
    val id: Int,
    val itemCount: Int,
    val iso6391: String,
    val listType: String,
    val name: String,
    val posterPath: String?,
    val posterUrls: List<String>? = emptyList()
)