package com.proyect.movielists.domine.models

data class MovieFavResponse(
    val page: Int,
    val results: List<MovieFav>,
    val totalPages: Int,
    val totalResults: Int
)

data class MovieFav(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val adult: Boolean,
    val video: Boolean,
    val originalLanguage: String,
    val genreIds: List<Int>
)


data class FavoriteRequest(
    val mediaType: String,
    val mediaId: Int,
    val isFavorite: Boolean
)

data class FavoriteResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String
)