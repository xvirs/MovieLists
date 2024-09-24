package com.proyect.movielists.data.datasource

import com.proyect.movielists.data.interfaces.MovieDataSource
import com.proyect.movielists.data.models.dto.MovieDetailsDto
import com.proyect.movielists.data.network.BaseClient
import com.proyect.movielists.utils.StatusResult
import io.ktor.client.call.body

class MovieDataSourceImpl(private val baseClient : BaseClient) : MovieDataSource {
    override suspend fun getMovie(movieId: String): StatusResult<MovieDetailsDto> {
        val endpoint = "/movie/$movieId"
        val errorMessage = "Error al obtener pelicula"
        val response = baseClient.get(
            url = endpoint,
            errorMessage = errorMessage,
        )
        return if (response.httpResponse != null) {
            StatusResult.Success(response.httpResponse.body<MovieDetailsDto>())
        } else {
            StatusResult.Error(response.errorMessage)
        }
    }
}