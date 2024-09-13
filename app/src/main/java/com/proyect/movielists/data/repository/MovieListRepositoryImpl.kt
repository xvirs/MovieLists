package com.proyect.movielists.data.repository

import com.proyect.movielists.data.interfaces.MovieListDataSource
import com.proyect.movielists.data.interfaces.SessionDataStore
import com.proyect.movielists.data.mappers.toDataModel
import com.proyect.movielists.data.mappers.toDomainModel
import com.proyect.movielists.domine.interfaces.MovieListRepository
import com.proyect.movielists.domine.models.AddMovieToListRequest
import com.proyect.movielists.domine.models.AddMovieToListResponse
import com.proyect.movielists.domine.models.CreateMovieListRequest
import com.proyect.movielists.domine.models.CreateMovieListResponse
import com.proyect.movielists.domine.models.RemoveMovieFromListRequest
import com.proyect.movielists.domine.models.RemoveMovieFromListResponse
import com.proyect.movielists.domine.models.RemoveListResponse
import com.proyect.movielists.domine.models.GetMovieListsResponse
import com.proyect.movielists.utils.StatusResult
import kotlinx.coroutines.flow.first

class MovieListRepositoryImpl(
    private val movieListDataSource: MovieListDataSource,
    private val sessionDataStore : SessionDataStore
) : MovieListRepository {

    override suspend fun createMovieList(
        createMovieListRequest: CreateMovieListRequest
    ): StatusResult<CreateMovieListResponse> {
        return when (val result = movieListDataSource.createMovieList(createMovieListRequest.toDataModel(), getRequestToken())) {
            is StatusResult.Success -> StatusResult.Success(result.value.toDomainModel())
            is StatusResult.Error -> StatusResult.Error(result.message)
        }
    }

    override suspend fun getMovieLists(): StatusResult<GetMovieListsResponse> {
        return when (val result = movieListDataSource.getMovieLists(getRequestToken(), getAccountId())) {
            is StatusResult.Success -> StatusResult.Success(result.value.toDomainModel())
            is StatusResult.Error -> StatusResult.Error(result.message)
        }
    }

    override suspend fun addMovieToList(
        addMovieToListRequest: AddMovieToListRequest,
        listId: String
    ): StatusResult<AddMovieToListResponse> {
        return when (val result = movieListDataSource.addMovieToList(addMovieToListRequest.toDataModel(), getRequestToken(), listId)) {
            is StatusResult.Success -> StatusResult.Success(result.value.toDomainModel())
            is StatusResult.Error -> StatusResult.Error(result.message)
        }
    }

    override suspend fun removeMovieFromList(
        removeMovieFromListRequest: RemoveMovieFromListRequest,
        listId: String
    ): StatusResult<RemoveMovieFromListResponse> {
        return when (val result = movieListDataSource.removeMovieFromList(removeMovieFromListRequest.toDataModel(), getRequestToken(), listId)) {
            is StatusResult.Success -> StatusResult.Success(result.value.toDomainModel())
            is StatusResult.Error -> StatusResult.Error(result.message)
        }
    }

    override suspend fun removeList(
        listId: String,
    ): StatusResult<RemoveListResponse> {
        return when (val result = movieListDataSource.removeList(getRequestToken(), listId)) {
            is StatusResult.Success -> StatusResult.Success(result.value.toDomainModel())
            is StatusResult.Error -> StatusResult.Error(result.message)
        }
    }

    private suspend fun getRequestToken() : String{
        val requestToken = when (sessionDataStore.sessionIdFlow.first()){
            is StatusResult.Success -> ( sessionDataStore.sessionIdFlow.first() as StatusResult.Success<String?>).value
            is StatusResult.Error -> { "Error al obtener el Token local de sesión" }
        }
        return requestToken!!
    }

    private suspend fun getAccountId() : String{
        val accountId = when (sessionDataStore.accountIdFlow.first()){
            is StatusResult.Success -> ( sessionDataStore.accountIdFlow.first() as StatusResult.Success<String?>).value
            is StatusResult.Error -> { "Error al obtener el ID de la cuenta" }
        }
        return accountId!!
    }
}