package com.proyect.movielists.presentation.models

data class MovieListUI(
    val name: String,
    val description: String,
    val movies: List<MovieUI>
)


data class MovieUI(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val releaseDate: String?,
    val voteAverage: Double?
)

data class MovieFavUI(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val releaseDate: String,
    val isFavorite: Boolean
)

data class MovieDetailsUI(
    val id: Int,
    val title: String,
    val overview: String?,
    val posterUrl: String?,
    val backdropUrl: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val runtime: Int?,
    val genres: List<String>,
    val tagline: String?,
    val status: String
)