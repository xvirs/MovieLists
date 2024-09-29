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

data class GetMovieListResponse(
    val createdBy: String,
    val description: String,
    val favoriteCount: Int,
    val id: Int,
    val language: String,
    val itemCount: Int,
    val movies: List<MovieItem> = emptyList(),
    val name: String,
    val page: Int,
    val posterPath: String? = null,
    val totalPages: Int,
    val totalResults: Int
)

data class MovieItem(
    val id: Int,
    val title: String? = null,
    val overview: String? = null,
    val posterPath: String? = null
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
