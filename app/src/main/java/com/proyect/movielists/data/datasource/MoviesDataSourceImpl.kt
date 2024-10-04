package com.proyect.movielists.data.datasource

import com.proyect.movielists.data.interfaces.MoviesDataSource
import com.proyect.movielists.data.models.MoviesResponseDto
import com.proyect.movielists.data.network.BaseClient
import com.proyect.movielists.utils.MovieListType
import com.proyect.movielists.utils.StatusResult
import io.ktor.client.call.body

class MoviesDataSourceImpl(private val baseClient: BaseClient) : MoviesDataSource {
    override suspend fun getPopularList(movieListType : MovieListType): StatusResult<MoviesResponseDto> {
        val endpoint = "/movie/${movieListType.endpoint}"
        val errorMessage = "Error al obtener la lista de peliculas ${movieListType.endpoint}"
        val response = baseClient.get(
            url = endpoint,
            errorMessage = errorMessage,
        )
        return if (response.httpResponse != null) {
            StatusResult.Success(response.httpResponse.body<MoviesResponseDto>())
        } else {
            StatusResult.Error(response.errorMessage)
        }
    }

    override suspend fun searchMovieList(query: String): StatusResult<MoviesResponseDto> {
        val endpoint = "/search/movie"
        val errorMessage = "Error al buscar la pelicula"
        val response = baseClient.get(
            url = endpoint,
            errorMessage = errorMessage,
            valueParams = mapOf("query" to query)
        )
        return if (response.httpResponse != null) {
            StatusResult.Success(response.httpResponse.body<MoviesResponseDto>())
        } else {
            StatusResult.Error(response.errorMessage)
        }
    }
}