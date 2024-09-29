package com.proyect.movielists.data.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// CreateMovieListDto
@Serializable
data class CreateMovieListRequestDto(
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("language") val language: String
)
@Serializable
data class CreateMovieListResponseDto(
    @SerialName("success") val success: Boolean,
    @SerialName("status_code") val statusCode: Int,
    @SerialName("status_message") val statusMessage: String,
    @SerialName("list_id") val listId: Int
)


// GetMovieListsDto
@Serializable
data class GetMovieListsResponseDto(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<ListItemDto>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)
@Serializable
data class ListItemDto(
    @SerialName("description") val description: String,
    @SerialName("favorite_count") val favoriteCount: Int,
    @SerialName("id") val id: Int,
    @SerialName("item_count") val itemCount: Int,
    @SerialName("iso_639_1") val iso6391: String,
    @SerialName("list_type") val listType: String,
    @SerialName("name") val name: String,
    @SerialName("poster_path") val posterPath: String?
)

@Serializable
data class GetMovieListResponseDto(
    @SerialName("created_by") val createdBy: String,  // Ahora es un String
    val description: String,
    @SerialName("favorite_count") val favoriteCount: Int,
    val id: Int,
    @SerialName("iso_639_1") val iso6391: String,
    @SerialName("item_count") val itemCount: Int,
    val items: List<MovieItemDto> = emptyList(),
    val name: String,
    val page: Int,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

@Serializable
data class MovieItemDto(
    val id: Int,
    val title: String? = null,
    val overview: String? = null,
    @SerialName("poster_path") val posterPath: String? = null
)



// AddMovieListMovieDto
@Serializable
data class AddMovieToListRequestDto(
    @SerialName("media_id") val mediaId: Int
)
@Serializable
data class AddMovieToListResponseDto(
    @SerialName("success") val success: Boolean,
    @SerialName("status_code") val statusCode: Int,
    @SerialName("status_message") val statusMessage: String
)


// RemoveListMovieDto
@Serializable
data class RemoveMovieFromListRequestDto(
    @SerialName("media_id") val mediaId: Int
)
@Serializable
data class RemoveMovieFromListResponseDto(
    @SerialName("success") val success: Boolean,
    @SerialName("status_code") val statusCode: Int,
    @SerialName("status_message") val statusMessage: String
)


// RemoveListDto
@Serializable
data class RemoveListResponseDto(
    @SerialName("success") val success: Boolean,
    @SerialName("status_code") val statusCode: Int,
    @SerialName("status_message") val statusMessage: String
)

