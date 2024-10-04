package com.proyect.movielists.data.datasource

import com.proyect.movielists.data.interfaces.MovieListDataSource
import com.proyect.movielists.data.models.AddMovieToListRequestDto
import com.proyect.movielists.data.models.AddMovieToListResponseDto
import com.proyect.movielists.data.models.CreateMovieListRequestDto
import com.proyect.movielists.data.models.CreateMovieListResponseDto
import com.proyect.movielists.data.models.GetMovieListResponseDto
import com.proyect.movielists.data.models.RemoveMovieFromListRequestDto
import com.proyect.movielists.data.models.RemoveMovieFromListResponseDto
import com.proyect.movielists.data.models.RemoveListResponseDto
import com.proyect.movielists.data.models.GetMovieListsResponseDto
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

    override suspend fun getMovieList(
        listId: String,
    ): StatusResult<GetMovieListResponseDto> {
        val endpoint = "/list/${listId}"
        val errorMessage = "Error al crear la lista"
        val response = baseClient.get(
            url = endpoint,
            errorMessage = errorMessage
        )
        return if (response.httpResponse != null) {
            StatusResult.Success(response.httpResponse.body<GetMovieListResponseDto>())
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
        val errorMessage = "Error al eliminar la lista"
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