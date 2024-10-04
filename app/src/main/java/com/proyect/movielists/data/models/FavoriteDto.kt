package com.proyect.movielists.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieFavResponseDTO(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<MovieFavDTO>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

@Serializable
data class MovieFavDTO(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("overview") val overview: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("popularity") val popularity: Double,
    @SerialName("adult") val adult: Boolean,
    @SerialName("video") val video: Boolean,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("genre_ids") val genreIds: List<Int>
)


@Serializable
data class FavoriteRequestDTO(
    @SerialName("media_type") val mediaType: String,
    @SerialName("media_id") val mediaId: Int,
    @SerialName("favorite") val favorite: Boolean
)


@Serializable
data class FavoriteResponseDTO(
    @SerialName("success") val success: Boolean,
    @SerialName("status_code") val statusCode: Int,
    @SerialName("status_message") val statusMessage: String
)