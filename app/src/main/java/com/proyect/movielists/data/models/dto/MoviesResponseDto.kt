package com.proyect.movielists.data.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponseDto(
    val page: Int,
    val results: List<MovieDto>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

@Serializable
data class MovieDto(
    val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("genre_ids") val genreIds: List<Int>,
    val id: Int,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int
)

@Serializable
data class MovieDetailsDto(
    val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("belongs_to_collection") val belongsToCollection: CollectionInfoDto?,
    val budget: Int,
    val genres: List<GenreDto>,
    val homepage: String?,
    val id: Int,
    @SerialName("imdb_id") val imdbId: String?,
    @SerialName("origin_country") val originCountry: List<String>,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    val overview: String?,
    val popularity: Double,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("production_companies") val productionCompanies: List<ProductionCompanyDto>,
    @SerialName("production_countries") val productionCountries: List<ProductionCountryDto>,
    @SerialName("release_date") val releaseDate: String,
    val revenue: Long,
    val runtime: Int?,
    @SerialName("spoken_languages") val spokenLanguages: List<SpokenLanguageDto>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int
)

@Serializable
data class GenreDto(
    val id: Int,
    val name: String
)

@Serializable
data class ProductionCompanyDto(
    val id: Int,
    @SerialName("logo_path") val logoPath: String?,
    val name: String,
    @SerialName("origin_country") val originCountry: String
)

@Serializable
data class ProductionCountryDto(
    @SerialName("iso_3166_1") val iso: String,
    val name: String
)

@Serializable
data class SpokenLanguageDto(
    @SerialName("english_name") val englishName: String,
    @SerialName("iso_639_1") val iso: String,
    val name: String
)

@Serializable
data class CollectionInfoDto(
    val id: Int,
    val name: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path") val backdropPath: String?
)