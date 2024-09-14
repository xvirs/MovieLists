package com.proyect.movielists.data.datasource

import com.proyect.movielists.data.interfaces.MovieListDataSource
import com.proyect.movielists.data.models.dto.AddMovieToListRequestDto
import com.proyect.movielists.data.models.dto.AddMovieToListResponseDto
import com.proyect.movielists.data.models.dto.CreateMovieListRequestDto
import com.proyect.movielists.data.models.dto.CreateMovieListResponseDto
import com.proyect.movielists.data.models.dto.RemoveMovieFromListRequestDto
import com.proyect.movielists.data.models.dto.RemoveMovieFromListResponseDto
import com.proyect.movielists.data.models.dto.RemoveListResponseDto
import com.proyect.movielists.data.models.dto.GetMovieListsResponseDto
import com.proyect.movielists.data.network.BaseClient
import com.proyect.movielists.utils.StatusResult
import io.ktor.client.call.body
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MovieListDataSourceImpl(private val baseClient: BaseClient) : MovieListDataSource {

    override suspend fun createMovieList(
        createMovieListRequestDto: CreateMovieListRequestDto,
        sessionId: String
    ): StatusResult<CreateMovieListResponseDto> {
        val endpoint = "/list"
        val errorMessage = "Error al crear la lista"
        val response = baseClient.post(
            url = endpoint,
            body = Json.encodeToString(createMovieListRequestDto),
            errorMessage = errorMessage,
            sessionId = sessionId,
            )
        return if (response.httpResponse != null) {
            StatusResult.Success(response.httpResponse.body<CreateMovieListResponseDto>())
        } else {
            StatusResult.Error(response.errorMessage)
        }
    }

    override suspend fun getMovieLists(
        sessionId: String,
        accountId : String
    ): StatusResult<GetMovieListsResponseDto> {
        val endpoint = "/account/${accountId}/lists"
        val errorMessage = "Error al crear la lista"
        val response = baseClient.get(
            url = endpoint,
            errorMessage = errorMessage,
            valueParams = mapOf("session_id" to sessionId)
        )
        return if (response.httpResponse != null) {
            StatusResult.Success(response.httpResponse.body<GetMovieListsResponseDto>())
        } else {
            StatusResult.Error(response.errorMessage)
        }
    }

    override suspend fun addMovieToList(
        addMovieToListRequestDto: AddMovieToListRequestDto,
        sessionId: String,
        listId : String
    ): StatusResult<AddMovieToListResponseDto> {
        val endpoint = "/list/${listId}/add_item"
        val errorMessage = "Error al crear la lista"
        val response = baseClient.post(
            url = endpoint,
            body = Json.encodeToString(addMovieToListRequestDto),
            errorMessage = errorMessage,
            sessionId = sessionId,
        )
        return if (response.httpResponse != null) {
            StatusResult.Success(response.httpResponse.body<AddMovieToListResponseDto>())
        } else {
            StatusResult.Error(response.errorMessage)
        }
    }

    override suspend fun removeMovieFromList(
        removeMovieFromListRequestDto: RemoveMovieFromListRequestDto,
        sessionId: String,
        listId : String
    ): StatusResult<RemoveMovieFromListResponseDto> {
        val endpoint = "/list/${listId}/remove_item"
        val errorMessage = "Error al crear la lista"
        val response = baseClient.post(
            url = endpoint,
            body = Json.encodeToString(removeMovieFromListRequestDto),
            errorMessage = errorMessage,
            sessionId = sessionId,
        )
        return if (response.httpResponse != null) {
            StatusResult.Success(response.httpResponse.body<RemoveMovieFromListResponseDto>())
        } else {
            StatusResult.Error(response.errorMessage)
        }
    }

    override suspend fun removeList(
        listId: String,
        sessionId: String
    ): StatusResult<RemoveListResponseDto> {
        val endpoint = "/list/${listId}"
        val errorMessage = "Error al crear la lista"
        val response = baseClient.delete(
            url = endpoint,
            errorMessage = errorMessage,
            sessionId = sessionId,
        )
        return if (response.httpResponse != null) {
            StatusResult.Success(response.httpResponse.body<RemoveListResponseDto>())
        } else {
            StatusResult.Error(response.errorMessage)
        }
    }

}