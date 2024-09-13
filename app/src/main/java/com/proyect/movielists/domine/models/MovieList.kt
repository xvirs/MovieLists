package com.proyect.movielists.domine.models

// CreateMovieList
data class CreateMovieListRequest(
    val name: String,
    val description: String,
    val language: String
)

data class CreateMovieListResponse(
    val success: Boolean,
    val statusCode: Int,
    val statusMessage: String,
    val listId: Int
)

// GetMovieLists
data class GetMovieListsResponse(
    val page: Int,
    val results: List<ListItem>,
    val totalPages: Int,
    val totalResults: Int
)

data class ListItem(
    val description: String,
    val favoriteCount: Int,
    val id: Int,
    val itemCount: Int,
    val iso6391: String,
    val listType: String,
    val name: String,
    val posterPath: String?
)

// AddMovieListMovie
data class AddMovieToListRequest(
    val mediaId: Int
)

data class AddMovieToListResponse(
    val success: Boolean,
    val statusCode: Int,
    val statusMessage: String
)

// RemoveListMovie
data class RemoveMovieFromListRequest(
    val mediaId: Int
)

data class RemoveMovieFromListResponse(
    val success: Boolean,
    val statusCode: Int,
    val statusMessage: String
)

// RemoveList
data class RemoveListResponse(
    val success: Boolean,
    val statusCode: Int,
    val statusMessage: String
)
