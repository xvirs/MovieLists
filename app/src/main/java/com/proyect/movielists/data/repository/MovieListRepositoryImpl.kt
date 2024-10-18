package com.proyect.movielists.data.repository

import com.proyect.movielists.data.interfaces.MovieListDataSource
import com.proyect.movielists.data.interfaces.SessionDataStore
import com.proyect.movielists.data.models.mappers.toDataModel
import com.proyect.movielists.data.models.mappers.toDomainModel
import com.proyect.movielists.domine.interfaces.MovieListRepository
import com.proyect.movielists.domine.models.AddMovieToListRequest
import com.proyect.movielists.domine.models.AddMovieToListResponse
import com.proyect.movielists.domine.models.CreateMovieListRequest
import com.proyect.movielists.domine.models.CreateMovieListResponse
import com.proyect.movielists.domine.models.GetMovieListResponse
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
        return when (val result = movieListDataSource.createMovieList(
            createMovieListRequestDto = createMovieListRequest.toDataModel(),
            sessionId = getRequestToken())) {
            is StatusResult.Success -> StatusResult.Success(result.value.toDomainModel())
            is StatusResult.Error -> StatusResult.Error(result.message)
        }
    }

    override suspend fun getMovieLists(): StatusResult<GetMovieListsResponse> {
        return when (val result = movieListDataSource.getMovieLists(
            sessionId = getRequestToken(),
            accountId = getAccountId())) {
            is StatusResult.Success -> StatusResult.Success(result.value.toDomainModel())
            is StatusResult.Error -> StatusResult.Error(result.message)
        }
    }

    override suspend fun getMovieList(listId: String): StatusResult<GetMovieListResponse> {
        return when (val result = movieListDataSource.getMovieList(
            listId = listId
        )) {
            is StatusResult.Success -> StatusResult.Success(result.value.toDomainModel())
            is StatusResult.Error -> StatusResult.Error(result.message)
        }
    }



    override suspend fun addMovieToList(
        addMovieToListRequest: AddMovieToListRequest,
        listId: String
    ): StatusResult<AddMovieToListResponse> {
        return when (val result = movieListDataSource.addMovieToList(
            addMovieToListRequestDto = addMovieToListRequest.toDataModel(),
            sessionId = getRequestToken(),
            listId = listId
        )) {
            is StatusResult.Success -> StatusResult.Success(result.value.toDomainModel())
            is StatusResult.Error -> StatusResult.Error(result.message)
        }
    }

    override suspend fun removeMovieFromList(
        removeMovieFromListRequest: RemoveMovieFromListRequest,
        listId: String
    ): StatusResult<RemoveMovieFromListResponse> {
        return when (val result = movieListDataSource.removeMovieFromList(
            removeMovieFromListRequestDto = removeMovieFromListRequest.toDataModel(),
            sessionId = getRequestToken(),
            listId = listId)) {
            is StatusResult.Success -> StatusResult.Success(result.value.toDomainModel())
            is StatusResult.Error -> StatusResult.Error(result.message)
        }
    }

    override suspend fun removeList(
        listId: String,
    ): StatusResult<RemoveListResponse> {
        return when (val result = movieListDataSource.removeList(
            listId = listId,
            sessionId = getRequestToken(),
        )) {
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
            is StatusResult.Success -> ( sessionDataStore.accountIdFlow.first() as StatusResult.Success<String>).value
            is StatusResult.Error -> { "Error al obtener el ID de la cuenta" }
        }
        return accountId!!
    }
}