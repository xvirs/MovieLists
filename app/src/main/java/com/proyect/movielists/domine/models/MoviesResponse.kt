package com.proyect.movielists.domine.models

data class MoviesResponse(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)

data class Movie(
    val adult: Boolean,
    val backdropPath: String?,
    val genreIds: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)

data class MovieDetails(
    val adult: Boolean,
    val backdropPath: String?,
    val belongsToCollection: CollectionInfo?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    val imdbId: String?,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String?,
    val popularity: Double,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int?,
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)

data class Genre(
    val id: Int,
    val name: String
)

data class ProductionCompany(
    val id: Int,
    val logoPath: String?,
    val name: String,
    val originCountry: String
)

data class ProductionCountry(
    val iso: String,
    val name: String
)

data class SpokenLanguage(
    val englishName: String,
    val iso: String,
    val name: String
)

data class CollectionInfo(
    val id: Int,
    val name: String,
    val posterPath: String?,
    val backdropPath: String?
)